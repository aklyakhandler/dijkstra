package com.akliakhandler.repository;

import com.akliakhandler.model.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NodeFileRepository {
    public Set<Node> readFromFile(Path path) {

        Set<Node> result = new HashSet<>();

        List<String> nodesLines;
        try {
            nodesLines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String nodeLine : nodesLines) {
            Node node = parseLine(nodeLine);
            if (node != null) {
                result.add(node);
            }
        }

        return result;
    }

    private Node parseLine(String nodeLine) {
        String[] strings = nodeLine.replace("(", "")
                .replace(")", "")
                .replace(",", "")
                .split(" ");

        return new Node(strings[0], Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Arrays.stream(strings).skip(3).collect(Collectors.toSet()));
    }

}
