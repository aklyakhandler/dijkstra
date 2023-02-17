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
        for (String ajdNodeName : currentNode.getAdjacentNodesNames()) {
            Node adjNode = getByName(ajdNodeName);
            if (distancesToStartNode.get(currentNode) - getDistanceSquared(currentNode, adjNode) == distancesToStartNode.get(adjNode)) {
                return adjNode;
            }
        }
        return null;
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
            Node notVisitedNodeWithMinDistance = null;
            for (Node notVisitedNode : notVisitedNodes) {
                if (notVisitedNodeWithMinDistance == null) {
                    notVisitedNodeWithMinDistance = notVisitedNode;
                    continue;
                }

                double notVisitedNodeDistance = distanceToFirst.getOrDefault(notVisitedNode, Double.MAX_VALUE);
                double notVisitedNodeWithMinDistanceDistance = distanceToFirst.getOrDefault(notVisitedNodeWithMinDistance, Double.MAX_VALUE);

                if (notVisitedNodeDistance < notVisitedNodeWithMinDistanceDistance) {
                    notVisitedNodeWithMinDistance = notVisitedNode;
                }
            }
            currentNode = notVisitedNodeWithMinDistance;
        }
        return distanceToFirst;
    }

    protected Node getByName(String name) {
        for (Node node : nodes) {
            if (name.equals(node.getName())) {
                return node;
            }
        }
        return null;
    }

    //skip square root for speed since it is monotonic function
    private double getDistanceSquared(Node node1, Node node2) {
        return Math.pow(node2.getX() - node1.getX(), 2) + Math.pow(node2.getY() - node1.getY(), 2);
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
