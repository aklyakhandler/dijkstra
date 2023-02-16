package com.akliakhandler.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {
    private final String name;
    private final long x;
    private final long y;
    private final Set<String> adjacentNodesNames;

    public Node(String name, long x, long y, Set<String> adjacentNodes) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.adjacentNodesNames = new HashSet<>(adjacentNodes);
    }

    public String getName() {
        return name;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public Set<String> getAdjacentNodesNames() {
        return adjacentNodesNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y && name.equals(node.name) && adjacentNodesNames.equals(node.adjacentNodesNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, x, y, adjacentNodesNames);
    }
}
