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
            while (node.getLeft() != null) {
                node = node.getLeft();
            }
            array[i] = node.getValue();
            if (node == root) {
                root = node.getRight();
            } else {
                if (node.getRight() != null) {
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
        ArrayList<Node>[] treeLines = new ArrayList[maxLevel + 1];
        ArrayList<String>[] treeLinesSymbols = new ArrayList[maxLevel + 1];
        for (int i = 0; i < maxLevel + 1; i++) {
            treeLines[i] = new ArrayList<>();
            treeLinesSymbols[i] = new ArrayList<>();
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            treeLines[node.getLevel()].add(node);
            balance(treeLines, treeLinesSymbols, node);
            if (node.getSide() == Side.LEFT) {
                while (treeLinesSymbols[node.getLevel()].size() <= treeLines[node.getLevel()].indexOf(node))
                    treeLinesSymbols[node.getLevel()].add(" ");
                treeLinesSymbols[node.getLevel()].set(treeLines[node.getLevel()].indexOf(node), "┌");
            } else {
                while (treeLinesSymbols[node.getLevel()].size() <= treeLines[node.getLevel()].indexOf(node))
                    treeLinesSymbols[node.getLevel()].add("─");
                treeLinesSymbols[node.getLevel()].set(treeLines[node.getLevel()].indexOf(node), "┐");
            }
            if (node.getLevel() + 1 < maxLevel + 1 && node.hasChild()) {
                while (treeLinesSymbols[node.getLevel() + 1].size() < treeLines[node.getLevel()].indexOf(node))
                    treeLinesSymbols[node.getLevel() + 1].add(" ");
                treeLinesSymbols[node.getLevel() + 1].add(treeLines[node.getLevel()].indexOf(node), "┴");
            }
            if (node.getLeft() != null) queue.add(node.getLeft());
            if (node.getRight() != null) queue.add(node.getRight());
        }

        for (int i = 0; i < maxLevel + 1; i++) {
            for (Node n : treeLines[i]) {
                if (n == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(n.getValue() + " ");
                }
            }
            System.out.println();
            if (i + 1 < maxLevel + 1) treeLinesSymbols[i + 1].forEach(n -> System.out.print(n + " "));
            System.out.println();
        }

        System.out.println();
    }

    private static void balance(ArrayList<Node>[] treeLines, ArrayList<String>[] treeLinesSymbols, Node node) {
        if (node == null || node.getLevel() == 0)
            return;
        int indexOfNode = treeLines[node.getLevel()].indexOf(node);
        int indexOfParent = treeLines[node.getLevel() - 1].indexOf(node.getParent());
        if (node.getSide() == Side.LEFT) {
            while (indexOfParent <= indexOfNode) {
                treeLines[node.getLevel() - 1].add(indexOfParent, null);
                if (treeLinesSymbols[node.getLevel() - 1].get(indexOfParent) != null && !treeLinesSymbols[node.getLevel() - 1].get(indexOfParent).equals(" ")) {
                    if (treeLinesSymbols[node.getLevel() - 1].get(indexOfParent).equals("┐")) {
                        treeLinesSymbols[node.getLevel() - 1].add(indexOfParent, "─");
                    } else {
                        if (!treeLinesSymbols[node.getLevel() - 1].get(indexOfParent).equals("┴")) {
                            treeLinesSymbols[node.getLevel() - 1].add(indexOfParent, "─");
                        }
                    }
                } else {
                    treeLinesSymbols[node.getLevel() - 1].add(indexOfParent, " ");
                }
                indexOfParent++;
            }
            for (int nodesIndex = indexOfParent + 1; nodesIndex < treeLines[node.getLevel() - 1].size(); nodesIndex++) {
                balance(treeLines, treeLinesSymbols, treeLines[node.getLevel() - 1].get(nodesIndex));
            }
        } else {
            while (indexOfParent >= indexOfNode) {
                if (indexOfNode == -1) {
                    treeLines[node.getLevel()].add(null);
                    if (treeLinesSymbols[node.getLevel()].get(indexOfNode - 1) != null) {
                        if (treeLinesSymbols[node.getLevel()].get(indexOfNode - 1).equals("┐")) {
                            treeLinesSymbols[node.getLevel()].add(indexOfNode - 1, "─");
                        } else {
                            treeLinesSymbols[node.getLevel()].add("─");
                        }
                    } else {
                        treeLinesSymbols[node.getLevel()].add(indexOfNode - 1, "─");
                    }
                } else {
                    treeLines[node.getLevel()].add(indexOfNode, null);
                    for (int nodesIndex = indexOfNode - 1; nodesIndex >= 0; nodesIndex--) {
                        balance(treeLines, treeLinesSymbols, treeLines[node.getLevel()].get(nodesIndex));
                    }
                }
                indexOfNode = treeLines[node.getLevel()].indexOf(node);
            }
        }
    }
}
