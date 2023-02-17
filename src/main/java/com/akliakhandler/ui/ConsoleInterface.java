package com.akliakhandler.ui;

import com.akliakhandler.manager.ApplicationManager;
import com.akliakhandler.model.Node;

import java.util.List;
import java.util.Scanner;

public class ConsoleInterface {
    private final ApplicationManager applicationManager = new ApplicationManager();

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
        String command = strings[0];
        if (command.equalsIgnoreCase("help")) {
            sendHelp();
        } else if (command.equalsIgnoreCase("read_file")) {
            readFile(strings[1]);
        } else if (command.equalsIgnoreCase("path")) {
            getPath(strings[1], strings[2]);
        } else if (command.equalsIgnoreCase("exit")) {
            exit();
        } else {
            System.out.println("Command unknown");
            sendHelp();
        }
    }

    private void sendHelp() {
        System.out.println("Commands:");
        System.out.println("•help");
        System.out.println("•read_file <file>");
        System.out.println("•path <a> <b>");
        System.out.println("•exit");
    }

    private void readFile(String fileName) {
        applicationManager.readFile(fileName);
        System.out.println("File was read.");
    }

    private void getPath(String nodeA, String nodeB) {
        List<Node> path = applicationManager.getPath(nodeA, nodeB);
        for (int i = 0; i < path.size(); i++) {
            Node node = path.get(i);
            System.out.print(node.getName() + " (" + node.getX() + ", " + node.getY() + ")");
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.printf("\nTotal distance: %s\n", applicationManager.evaluateDistance(path));
    }

    private void exit() {
        System.exit(0);
    }
}

