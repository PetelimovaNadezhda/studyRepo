package com.example.sortAlgorithm;

import com.example.tree.Node;
import com.example.tree.Side;

import static com.example.tree.Side.*;

public class SplaySort {

    static int maxLevel = 0;

    public static int[] sort(int[] array) {
        Node root = treeGenerate(array);
        return generateSortArrayByTree(root, array.length);
    }

    private static int[] generateSortArrayByTree(Node root, int length) {
        int[] array = new int[length];
        Node node = root;
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        balanceSplayTree(node);
        for (int i = 0; i < length; i++) {
            array[i] = node.getValue();
            node = node.getRight();
        }
        return array;
    }

    private static Node treeGenerate(int[] array) {
        Node root = new Node(array[0], null);
        root.setLevel(0);
        for (int i = 1; i < array.length; i++) {
            Node node = root;
            int level = 0;
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
                        root = balanceSplayTree(node.getRight());
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
                        root = balanceSplayTree(node.getLeft());
                        break;
                    }
                }
            }
        }
        return root;
    }

    private static Node balanceSplayTree(Node node) {
        while (node.getParent() != null &&
                node.getParent().getParent() != null &&
                node.getParent().getParent().getParent() != null) {
            Node parentNode = node;
            while (parentNode.getParent().getParent().getParent() != null) {
                parentNode = parentNode.getParent();
            }
            balanceSplayTree(parentNode);
        }
        if (node.getParent() != null) {
            if (node.getParent().getParent() != null) {
                Side parentSide = node.getParent().getSide();
                if (node.getSide().equals(parentSide)) {
                    return zigZig(node);
                } else {
                    return zigZag(node);
                }
            } else {
                return zig(node);
            }
        }
        return node;
    }

    public static Node zig(Node node) {
        Side side = node.getSide();
        Node child = node.getChild(getOtherSide(side));

        node.setChild(getOtherSide(side), node.getParent());
        node.getParent().setSide(side);
        node.getParent().setChild(side, child);
        if (child != null) {
            child.setSide(side);
        }

        node.getParent().setParent(node);
        node.setParent(null);

        return node;
    }

    public static Node zigZig(Node node) {
        Side side = node.getSide();

        Node child = node.getChild(getOtherSide(side));
        Node parentChild = node.getParent().getChild(getOtherSide(side));

        node.setChild(getOtherSide(side), node.getParent());
        node.getParent().setSide(getOtherSide(side));
        node.getParent().setChild(side, node.getParent().getParent());
        node.getParent().getParent().setSide(side);
        node.getParent().setChild(getOtherSide(side), child);
        if (child != null) {
            child.setSide(getOtherSide(side));
        }
        node.getParent().getParent().setChild(side, parentChild);
        if (parentChild != null) {
            parentChild.setSide(side);
        }

        node.getParent().getParent().setParent(node.getParent());
        node.getParent().setParent(node);
        node.setParent(null);

        return node;
    }

    public static Node zigZag(Node node) {
        Side side = node.getSide();

        Node childSideAsNode = node.getChild(getOtherSide(side));
        Node childOtherSideAsNode = node.getChild(side);
        node.getParent().setChild(getOtherSide(side), childOtherSideAsNode);
        if (childOtherSideAsNode != null) {
            childOtherSideAsNode.setSide(getOtherSide(side));
        }
        node.getParent().getParent().setChild(getOtherSide(side), childSideAsNode);
        if (childSideAsNode != null) {
            childSideAsNode.setSide(getOtherSide(side));
        }

        node.getParent().getParent().setParent(node);
        node.getParent().setParent(node);
        node.setParent(null);

        return node;
    }
}
