package com.akliakhandler.repository;

import com.akliakhandler.model.Node;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class NodeFileRepositoryTest {

    @Test
    public void test() throws URISyntaxException {
        NodeFileRepository nodeFileRepository = new NodeFileRepository();
        Set<Node> nodes = nodeFileRepository.readFromFile(Path.of(getClass().getClassLoader().getResource("sample-nodes.txt").toURI()));
        assertEquals(3, nodes.size());
    }
}