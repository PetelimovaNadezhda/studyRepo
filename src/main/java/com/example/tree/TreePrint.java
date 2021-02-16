package com.example.tree;

import com.example.sortAlgorithm.Tree;

import java.util.ArrayList;
import java.util.LinkedList;

public class TreePrint {

    static ArrayList<Node>[] currentNodes;

    @SuppressWarnings("unchecked")
    public static ArrayList<Node>[] buildAndPrintTreeToConsole(Node root, Tree tree) {
        int maxLengthOfNumber = tree.getMaxLengthOfNumber() + 1;
        int maxLevel = tree.getMaxLevel();
        ArrayList<Node>[] nodes = new ArrayList[maxLevel + 1];
        ArrayList<String>[] edges = new ArrayList[maxLevel + 1];
        for (int i = 0; i < maxLevel + 1; i++) {
            nodes[i] = new ArrayList<>();
            edges[i] = new ArrayList<>();
        }
        //BFS
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.getLevel() > 0) {
                //if not root
                int indexOfParent = nodes[node.getLevel() - 1].indexOf(node.getParent());
                //try add new node to right position if possible
                addNewNode(nodes, node, indexOfParent);
            } else {
                //if root than just add
                nodes[node.getLevel()].add(node);
            }
            //fix position by new node
            rebalance(nodes, edges, node);
            //add ┴ or ┘ or └
            addParentsEdgeToChild(maxLevel, nodes, edges, node);
            if (node.getLeft() != null) queue.add(node.getLeft());
            if (node.getRight() != null) queue.add(node.getRight());
            currentNodes = nodes;
        }

        printTree(nodes, edges, maxLevel, maxLengthOfNumber);
        System.out.println();

        return nodes;
    }

    private static void addNewNode(ArrayList<Node>[] nodes, Node node, int indexOfParent) {
        if (node.getSide() == Side.LEFT) {
            if (nodes[node.getLevel()].size() > indexOfParent && indexOfParent > 0) {
                if (nodes[node.getLevel()].get(indexOfParent - 1) == null) {
                    nodes[node.getLevel()].set(indexOfParent - 1, node);
                } else {
                    nodes[node.getLevel()].add(node);
                }
            } else {
                while (nodes[node.getLevel()].size() < indexOfParent - 1) {
                    nodes[node.getLevel()].add(null);
                }
                nodes[node.getLevel()].add(node);
            }
        }
        if (node.getSide() == Side.RIGHT) {
            if (nodes[node.getLevel()].size() > indexOfParent + 1) {
                if (nodes[node.getLevel()].get(indexOfParent + 1) == null) {
                    nodes[node.getLevel()].set(indexOfParent + 1, node);
                } else {
                    nodes[node.getLevel()].add(node);
                }
            } else {
                while (nodes[node.getLevel()].size() < indexOfParent + 1) {
                    nodes[node.getLevel()].add(null);
                }
                nodes[node.getLevel()].add(node);
            }
        }
    }

    private static void addParentsEdgeToChild(int maxLevel,
                                              ArrayList<Node>[] nodes,
                                              ArrayList<String>[] edges,
                                              Node node) {
        if (node.getLevel() < maxLevel && node.hasChild()) {
            int indexOfNode = nodes[node.getLevel()].indexOf(node);
            while (edges[node.getLevel()].size() <= indexOfNode)
                edges[node.getLevel()].add(" ");
            if (node.hasLeftChild() && node.hasRightChild()) {
                edges[node.getLevel()].set(indexOfNode, "┴");
            } else {
                if (node.hasLeftChild())
                    edges[node.getLevel()].set(indexOfNode, "┘");
                if (node.hasRightChild())
                    edges[node.getLevel()].set(indexOfNode, "└");
            }
        }
    }

    private static void rebalance(ArrayList<Node>[] nodes,
                                  ArrayList<String>[] edges,
                                  Node node) {
        if (node == null || node.getLevel() == 0)
            return;
        int indexOfNode = nodes[node.getLevel()].indexOf(node);
        int indexOfParent = nodes[node.getLevel() - 1].indexOf(node.getParent());
        if (node.getSide() == Side.LEFT) {
            if (indexOfParent <= indexOfNode) {
                int level = node.getLevel() - 1;
                shift(nodes, edges, node, indexOfNode, level);
            }
            while (edges[node.getLevel() - 1].size() <= indexOfNode) {
                edges[node.getLevel() - 1].add(" ");
            }
            edges[node.getLevel() - 1].set(indexOfNode, "┌");
        }
        if (node.getSide() == Side.RIGHT) {
            if (edges[node.getLevel() - 1].size() > indexOfNode &&
                    !edges[node.getLevel() - 1].get(indexOfNode).equals(" ")) {
                int level = node.getLevel() - 1;
                shift(nodes, edges, node, indexOfNode, level);
            }
            while (edges[node.getLevel() - 1].size() <= indexOfNode) {
                edges[node.getLevel() - 1].add("─");
            }
            edges[node.getLevel() - 1].set(indexOfNode, "┐");
        }
    }

    private static void shift(ArrayList<Node>[] nodes,
                              ArrayList<String>[] edges,
                              Node node,
                              int indexOfNode,
                              int level) {
        while (level >= 0) {
            if (edges[level].size() > indexOfNode) {
                if ((edges[level].get(indexOfNode).equals("┴") ||
                        edges[level].get(indexOfNode).equals("┘") ||
                        edges[level].get(indexOfNode).equals("└")) && level != node.getLevel() - 1) {
                    if (nodes[level + 1].size() > indexOfNode)
                        nodes[level + 1].remove(indexOfNode);
                    break;
                }
                if (edges[level].get(indexOfNode).equals("─") ||
                        edges[level].get(indexOfNode).equals("┐")) {
                    edges[level].add(indexOfNode, "─");
                } else {
                    edges[level].add(indexOfNode, " ");
                }
            }
            if (nodes[level].size() > indexOfNode)
                nodes[level].add(indexOfNode, null);
            level--;
        }
    }

    private static void printTree(ArrayList<Node>[] nodes,
                                  ArrayList<String>[] edges,
                                  int maxLevel,
                                  int maxLengthOfNumber) {
        for (int level = 0; level < maxLevel + 1; level++) {
            //print nodes
            for (Node node : nodes[level]) {
                if (node == null) {
                    System.out.print(" ".repeat(maxLengthOfNumber));
                } else {
                    int length = (int) (Math.log10(node.getValue()) + 1);
                    int padding = Math.max(maxLengthOfNumber - length - 1, 1);
                    System.out.print(" ");
                    System.out.print(node.getValue());
                    System.out.print(" ".repeat(padding - 1));
                }
            }
            System.out.println();
            //print edges
            for (int i = 0; i < edges[level].size(); i++) {
                if (edges[level].get(i).equals(" ") || edges[level].get(i).equals("─"))
                    System.out.print(edges[level].get(i).repeat(maxLengthOfNumber));
                else {
                    if (edges[level].get(i).equals("┌") || edges[level].get(i).equals("└")) {
                        System.out.print(" ");
                        System.out.print(edges[level].get(i));
                        System.out.print("─".repeat(maxLengthOfNumber - 2));
                        continue;
                    }
                    if (edges[level].get(i).equals("┐") || edges[level].get(i).equals("┘")) {
                        System.out.print("─");
                        System.out.print(edges[level].get(i));
                        System.out.print(" ".repeat(maxLengthOfNumber - 2));
                        continue;
                    }
                    System.out.print("─");
                    System.out.print(edges[level].get(i));
                    System.out.print("─".repeat(maxLengthOfNumber - 2));
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
