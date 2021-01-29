package com.example.tree;

import com.example.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

import static com.example.sortAlgorithm.TreeSort.treeGenerate;

@RestController
public class TreeController {

    @Autowired
    private NodeRepository nodeRepository;

    @GetMapping("/tree")
    Iterable<NodeTable> all() {
        System.out.println("====in progress=====");
        nodeRepository.deleteAll();
        Node root = treeGenerate(Main.array);
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        Node node;
        while (!queue.isEmpty()) {
            node = queue.poll();
            if (node.getLeft() != null) queue.add(node.getLeft());
            if (node.getRight() != null) queue.add(node.getRight());
            nodeRepository.save(new NodeTable(node));
        }
        return nodeRepository.findAll();
    }
}