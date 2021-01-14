package com.company.sortAlgorithm;

import com.company.tree.Node;

import java.util.Arrays;

public class TreeSort {

    public static int[] sort(int[] array) {
        Node root = treeGenerate(array);
        return generateSortArrayByTree(root, array.length);
    }

    private static int[] generateSortArrayByTree(Node root, int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            Node node = root;
            while (node.getLeft() != null) {
                node = node.getLeft();
            }
            array[i] = node.getValue();
            if (node.getRight() != null) {
                if (node == root) {
                    root = node.getRight();
                } else {
                    node.setLeft(node.getRight());
                }
            }
            if (node.getLeft() == null && node.getRight() == null) {
                Node parent = node.getParent();
                if (parent.getRight() == node) {
                    parent.setRight(null);
                } else {
                    parent.setLeft(null);
                }
            }
        }
        return array;
    }

    private static Node treeGenerate(int[] array) {
        Node root = new Node(array[0], null);
        for (int i = 1; i < array.length; i++) {
            Node node = root;
            while (node != null) {
                if (node.getValue() <= array[i]) {
                    if (node.getRight() != null) {
                        node = node.getRight();
                    } else {
                        node.setRight(new Node(array[i], node));
                        break;
                    }
                }
                if (node.getValue() > array[i]) {
                    if (node.getLeft() != null) {
                        node = node.getLeft();
                    } else {
                        node.setLeft(new Node(array[i], node));
                        break;
                    }
                }
            }
        }
        return root;
    }
}
