package com.company.sortAlgorithm;

import com.company.tree.Node;

public class TreeSort {

    public static int[] sort(int[] array) {
        Node root = treeGenerate(array);
        return generateSortArray(root, array.length);
    }

    private static int[] generateSortArray(Node root, int length) {
        int[] array = new int[length];
        Node node = root;
        for (int i = 1; i < length; i++) {
            while (node.getLeft() != null) {
                node = node.getLeft();
            }
            if (node.getRight() != null) {
                array[i] = node.getValue();
                node = node.getRight();
            }
            array[i] = node.getValue();
        }
        return array;
    }

    private static Node treeGenerate(int[] array) {
        Node root = new Node(array[0]);
        for (int i = 1; i < array.length; i++) {
            Node node = root;
            boolean isFound = false;
            while (!isFound) {
                if (node.getValue() > array[i] && node.getRight() != null) {
                    node = node.getRight();
                }
                if (node.getValue() < array[i] && node.getLeft() != null) {
                    node = node.getLeft();
                }
                if (node.getValue() > array[i] && node.getRight() == null) {
                    node.setRight(new Node(array[i]));
                    node = node.getRight();
                    isFound = true;
                }
                if (node.getValue() < array[i] && node.getLeft() == null) {
                    node.setLeft(new Node(array[i]));
                    node = node.getLeft();
                    isFound = true;
                }
            }
        }
        return root;
    }
}
