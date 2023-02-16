package com.akliakhandler.manager;

import com.akliakhandler.model.Node;
import com.akliakhandler.repository.NodeFileRepository;
import com.akliakhandler.search.DijkstraGraphSearch;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ApplicationManager {
    private final NodeFileRepository nodeFileRepository = new NodeFileRepository();
    private DijkstraGraphSearch dijkstraGraphSearch;

    public void readFile(String stringPath) {
        Path path = parseStringPath(stringPath);

        dijkstraGraphSearch = new DijkstraGraphSearch(nodeFileRepository.readFromFile(path));
    }

    private Path parseStringPath(String stringPath) {
        Path result = Paths.get(stringPath);
        if (result.toFile().exists() && !result.toFile().isDirectory()) return result;

        String[] strings = stringPath.split(File.separator);
        result = Paths.get(strings[0], Arrays.copyOfRange(strings, 1, strings.length));
        if (result.toFile().exists() && !result.toFile().isDirectory()) return result;

        result = Paths.get(getClass().getClassLoader().getResource(stringPath).getPath());
        if (result.toFile().exists() && !result.toFile().isDirectory()) return result;

        throw new IllegalArgumentException(String.format("File '%s' not found", stringPath));
    }

    public List<Node> getPath(String node1, String node2) {
        return dijkstraGraphSearch.findShortestPath(node1, node2);
    }

    public Double evaluateDistance(List<Node> nodes) {
        Double result = 0.;
        if (nodes.size() > 1) {
            for (int i = 1; i < nodes.size(); i++) {
                Node right = nodes.get(i);
                Node left = nodes.get(i - 1);
                //euclidean distance
                result += Math.pow(Math.pow(right.getX() - left.getX(), 2) + Math.pow(right.getY() - left.getY(), 2), 0.5);
            }
        }
        return result;
    }

}
