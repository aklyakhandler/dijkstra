package com.akliakhandler.search;

import com.akliakhandler.model.Node;

import java.util.*;

public class DijkstraGraphSearch {
    private final Set<Node> nodes;

    public DijkstraGraphSearch(Set<Node> nodes) {
        this.nodes = new HashSet<>(nodes);
    }

    public List<Node> findShortestPath(String firstNodeName, String targetNodeName) {
        Map<Node, Double> distancesToStartNode = findDistancesToStartNode(firstNodeName);
        Node currentNode = getByName(targetNodeName);
        Node firstNode = getByName(firstNodeName);

        List<Node> result = new LinkedList<>();
        while (!Objects.equals(currentNode, firstNode)) {
            result.add(currentNode);
            currentNode = getNextClosestNode(distancesToStartNode, currentNode);
        }
        result.add(firstNode);

        Collections.reverse(result);

        return result;
    }

    private Node getNextClosestNode(Map<Node, Double> distancesToStartNode, Node currentNode) {
        return currentNode.getAdjacentNodesNames().stream()
                .map(this::getByName)
                .filter(node -> distancesToStartNode.get(currentNode) - getDistanceSquared(currentNode, node) == distancesToStartNode.get(node))
                .findFirst()
                .orElseThrow();
    }

    protected Map<Node, Double> findDistancesToStartNode(String firstNode) {
        Set<Node> notVisitedNodes = new HashSet<>(nodes);
        Map<Node, Double> distanceToFirst = new HashMap<>();

        Node currentNode = getByName(firstNode);
        distanceToFirst.put(currentNode, 0.);

        while (notVisitedNodes.size() > 0) {
            for (String nextNodeName : currentNode.getAdjacentNodesNames()) {
                Node nextNode = getByName(nextNodeName);

                double distanceFromNextToCurrent = getDistanceSquared(currentNode, nextNode);
                double distanceFromStartToNext = distanceFromNextToCurrent + distanceToFirst.get(currentNode);

                if (distanceToFirst.containsKey(nextNode)) {
                    if (distanceToFirst.get(nextNode) > distanceFromStartToNext) {
                        distanceToFirst.put(nextNode, distanceFromStartToNext);
                    }
                } else {
                    distanceToFirst.put(nextNode, distanceFromStartToNext);
                }
            }
            notVisitedNodes.remove(currentNode);
            currentNode = notVisitedNodes.stream()
                    .min(Comparator.comparing(node -> distanceToFirst.getOrDefault(node, Double.MAX_VALUE)))
                    .orElse(null);
        }
        return distanceToFirst;
    }

    protected Node getByName(String name) {
        return nodes.stream()
                .filter(node -> Objects.equals(node.getName(), name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Node with name '%s' not found", name)));
    }

    //skip square root for speed since it is monotonic function
    private double getDistanceSquared(Node node1, Node node2) {
        return Math.pow(node2.getX() - node1.getX(), 2) + Math.pow(node2.getY() - node1.getY(), 2);
    }
}
