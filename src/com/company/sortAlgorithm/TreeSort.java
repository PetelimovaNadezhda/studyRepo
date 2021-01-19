package com.company.sortAlgorithm;

import com.company.tree.Node;
import com.company.tree.Side;

import java.util.*;

public class TreeSort {

    static int maxLevel;
    static int maxLengthOfNumber;

    public static int[] sort(int[] array) {
        Node root = treeGenerate(array);
        treePrint(root);
        return generateSortArrayByTree(root, array.length);
    }

    private static int[] generateSortArrayByTree(Node root, int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            Node node = root;
            while (node.getLeft() != null && node.getLeft().getValue() != null) {
                node = node.getLeft();
            }
            array[i] = node.getValue();
            if (node == root) {
                root = node.getRight();
            } else {
                if (node.getRight() != null && node.getRight().getValue() != null) {
                    node.getRight().setSide(node.getSide())
                            .setParent(node.getParent());
                    node.getParent().setChild(node.getSide(), node.getRight());
                } else {
                    node.getParent().setChild(node.getSide(), null);
                }
            }
        }
        return array;
    }

    private static Node treeGenerate(int[] array) {
        Node root = new Node(array[0], null);
        root.setLevel(0);
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
                        node.setRight(new Node(array[i], node))
                                .setSide(Side.RIGHT)
                                .setParent(node)
                                .setLevel(++level);
                        if (maxLevel < level) maxLevel = level;
                        break;
                    }
                }
                if (node.getValue() > array[i]) {
                    if (node.getLeft() != null) {
                        node = node.getLeft();
                        level++;
                    } else {
                        node.setLeft(new Node(array[i], node))
                                .setSide(Side.LEFT)
                                .setParent(node)
                                .setLevel(++level);
                        if (maxLevel < level) maxLevel = level;
                        break;
                    }
                }
            }
        }
        return root;
    }


    public static void treePrint(Node root) {
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        int level = -1;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            //for new line
            if (node.getLevel() != level) {
                tabsBeforeFirstSymbol(node);
                level++;
            }
            printSymbolAndTabsAfter(node);
            //if left
            if (node.getLeft() == null && node.getLevel() < maxLevel) {
                node.setLeft(new Node(null, node))
                        .setLevel(node.getLevel() + 1)
                        .setParent(node);
            }
            if (node.getLeft() != null) queue.add(node.getLeft());
            //if right
            if (node.getRight() == null && node.getLevel() < maxLevel) {
                node.setRight(new Node(null, node))
                        .setLevel(node.getLevel() + 1)
                        .setParent(node);
            }
            if (node.getRight() != null) queue.add(node.getRight());
        }
        System.out.println();
    }

    private static void printSymbolAndTabsAfter(Node node) {
        //padding
        int length = 1;
        if (node.getValue() != null) {
            length = (int) (Math.log10(node.getValue()) + 1);
        }
        for (int j = 0; j < maxLengthOfNumber - length; j++) {
            System.out.print(" ");
        }
        //print value
        System.out.print(Objects.requireNonNullElse(node.getValue(), "x"));
        //print spaces between values
        for (int j = 0; j < (Math.pow(2, node.getLevel())) / 2; j++) {
            System.out.print(" ");
        }
    }

    private static void tabsBeforeFirstSymbol(Node node) {
        System.out.println();
        for (int j = 0; j < (Math.pow(2, maxLevel) - Math.pow(2, node.getLevel())) / Math.pow(2, node.getLevel()); j++) {
            System.out.print(" ");
        }
    }
}
