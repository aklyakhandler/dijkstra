package com.akliakhandler.search;

import com.akliakhandler.model.Node;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class DijkstraGraphSearchTest {

    @Test
    public void findDistancesToStartNodeTest() {
        DijkstraGraphSearch dijkstraGraphSearch = new DijkstraGraphSearch(testSet());
        Map<Node, Double> distancesToStartNode = dijkstraGraphSearch.findDistancesToStartNode("A");
        assertEquals(325, distancesToStartNode.get(dijkstraGraphSearch.getByName("D")), 0.);
    }

    @Test
    public void findShortestPathTest() {
        DijkstraGraphSearch dijkstraGraphSearch = new DijkstraGraphSearch(testSet());
        List<Node> path = dijkstraGraphSearch.findShortestPath("A", "D");
        assertEquals(3, path.size());
        assertEquals(dijkstraGraphSearch.getByName("A"), path.get(0));
        assertEquals(dijkstraGraphSearch.getByName("B"), path.get(1));
        assertEquals(dijkstraGraphSearch.getByName("D"), path.get(2));
    }

    private Set<Node> testSet() {
        Set<Node> testSet = new HashSet<>();
        testSet.add(new Node("A", 0, 0, Set.of("B", "C")));
        testSet.add(new Node("B", 10, 10, Set.of("A", "C", "D")));
        testSet.add(new Node("C", -10, 10, Set.of("A", "B", "D")));
        testSet.add(new Node("D", 20, 15, Set.of("B", "C")));
        return testSet;
    }

}