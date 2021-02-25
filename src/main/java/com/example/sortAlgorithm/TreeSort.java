package com.example.sortAlgorithm;

import com.example.tree.Node;
import com.example.tree.Side;
import com.example.tree.Tree;

public class TreeSort implements Tree {

    public static int maxLevel;
    public static int maxLengthOfNumber;

    public static int[] sort(int[] array) {
        Node root = treeGenerate(array);
//        buildAndPrintTreeToConsole(root, new TreeSort());
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
            if (node == root) {
                root = node.getRight();
            } else {
                if (node.getRight() != null) {
                    node.getRight().setSide(Side.LEFT);
                    node.setParent(node.getParent());
                    node.getParent().setChild(Side.LEFT, node.getRight());
                } else {
                    node.getParent().setChild(Side.LEFT, null);
                }
            }
        }
        return array;
    }

    public static Node treeGenerate(int[] array) {
        Node root = new Node(array[0], null, null, 0);
        maxLengthOfNumber = (int) (Math.log10(root.getValue()) + 1);
        for (int i = 1; i < array.length; i++) {
            int length = (int) (Math.log10(array[i]) + 1);
            if (maxLengthOfNumber < length) maxLengthOfNumber = length;
            int level = 0;
            Node node = root;
            while (node != null) {
                if (node.getValue() <= array[i]) {
                    if (node.getRight() != null) {
                        node = node.getRight();
                        level++;
                    } else {
                        node.setRight(new Node(array[i], node, Side.RIGHT, ++level));
                        if (maxLevel < level) maxLevel = level;
                        break;
                    }
                }
                if (node.getValue() > array[i]) {
                    if (node.getLeft() != null) {
                        node = node.getLeft();
                        level++;
                    } else {
                        node.setLeft(new Node(array[i], node, Side.LEFT, ++level));
                        if (maxLevel < level) maxLevel = level;
                        break;
                    }
                }
            }
        }
        return root;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getMaxLengthOfNumber() {
        return maxLengthOfNumber;
    }
}
