package com.akliakhandler.ui;

import com.akliakhandler.manager.ApplicationManager;
import com.akliakhandler.model.Node;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleInterface {
    private final ApplicationManager applicationManager = new ApplicationManager();
    private final Map<Command, Consumer<String[]>> commandMap = Map.of(
            Command.HELP, (_ignored -> this.sendHelp()),
            Command.READ_FILE, verifyArgs(1, strings -> this.readFile(strings[1])),
            Command.PATH, verifyArgs(2, strings -> this.getPath(strings[1], strings[2])),
            Command.EXIT, (_ignored -> this.exit())
    );

    public void loadApplication() {
        System.out.println("This is an implementation of Dijkstra search algorithm.");
        sendHelp();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            try {
                parseCommand(command);
            } catch (IllegalArgumentException e) {
                System.out.println("Command not parsed");
                sendHelp();
            }
        }
    }

    private void parseCommand(String commandNameWithArgs) {
        String[] strings = commandNameWithArgs.split(" ");
        Command command = Command.parseName(strings[0]);
        commandMap.get(command).accept(strings);
    }

    private Consumer<String[]> verifyArgs(int requiredArgs, Consumer<String[]> consumer) {
        return strings -> {
            if (strings.length == requiredArgs + 1) {
                consumer.accept(strings);
            } else {
                System.out.println("Argument count mismatch");
                sendHelp();
            }
        };
    }

    private void sendHelp() {
        System.out.println("Commands:");
        for (Command command : Command.values()) {
            System.out.printf("\t• %s - %s\n", Stream.concat(Stream.of(command.name().toLowerCase()), command.arguments.stream().map(s -> "<" + s + ">"))
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(" ")), command.description);
        }
    }

    private void readFile(String fileName) {
        try {
            applicationManager.readFile(fileName);
            System.out.println("File was read.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getPath(String nodeA, String nodeB) {
        try {
            List<Node> path = applicationManager.getPath(nodeA, nodeB);
            System.out.println(path.stream()
                    .map(node -> String.format("%s (%s, %s)", node.getName(), node.getX(), node.getY())).collect(Collectors.joining(" -> ")));
            System.out.printf("Total distance: %s\n", applicationManager.evaluateDistance(path));
        } catch (RuntimeException e) {
            System.out.printf("Exception raised while getting path: %s\n", e.getMessage());
        }
    }

    private void exit() {
        System.exit(0);
    }

    enum Command {
        HELP(null, "shows this message"),
        READ_FILE(List.of("file"), "reads file for further processing"),
        PATH(List.of("nodeA", "nodeB"), "finds shortest path between nodeA and nodeB (file data used as source)"),
        EXIT(null, "stops application");
        final List<String> arguments = new ArrayList<>();
        final String description;

        Command(List<String> arguments, String description) {
            if (arguments != null) this.arguments.addAll(arguments);
            this.description = description;
        }

        static Command parseName(String commandName) {
            return Stream.of(Command.values()).filter(command -> command.name().equalsIgnoreCase(commandName))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }
}

