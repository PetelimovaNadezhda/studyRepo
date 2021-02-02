package com.example.tree;

import com.example.Main;
import com.example.sortAlgorithm.TreeSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;

import static com.example.sortAlgorithm.TreeSort.treeGenerate;
import static com.example.tree.TreePrint.buildAndPrintTreeToConsole;

@RestController
public class TreeController {

    @Autowired
    private NodeRepository nodeRepository;

    @GetMapping("/tree")
    Iterable<NodeTable> all() {
        System.out.println("====in progress=====");
        nodeRepository.deleteAll();
        Node root = treeGenerate(Main.array);
        addCoorodinatesToTree(root);
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

    private static void addCoorodinatesToTree(Node root) {
        ArrayList<Node>[] coordinates = buildAndPrintTreeToConsole(root);
        for (int i = 0; i < coordinates.length; i++) {
            for (int j = 0; j < coordinates[i].size(); j++) {
                if (coordinates[i].get(j) != null) {
                    coordinates[i].get(j).setCoordX(j);
                    coordinates[i].get(j).setCoordY(i);
                }
            }
        }
    }
}