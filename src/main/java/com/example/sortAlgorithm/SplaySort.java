package com.example.sortAlgorithm;

import com.example.tree.Node;
import com.example.tree.Side;
import com.example.tree.Tree;

import java.util.LinkedList;

import static com.example.tree.Side.getOtherSide;

public class SplaySort implements Tree {

    public static int maxLevel = 0;
    public static int maxLengthOfNumber = 0;

    public static int[] sort(int[] array) {
        Node root = treeGenerate(array);
//        buildAndPrintTreeToConsole(root, new SplaySort());
        return generateSortArrayByTree(root, array.length);
    }

    // Should be like this
    //                   108
    //                ┌───┴───┐
    //                94     124
    //            ┌───┘       └───┐
    //            91             134
    //        ┌───┘               └───┐
    //        78                     135
    private static int[] generateSortArrayByTree(Node root, int length) {
        int[] array = new int[length];
        Node node = root;
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        int i = 0;
        while (node.getParent() != null) {
            array[i] = node.getValue();
            i++;
            node = node.getParent();
        }
        while (node != null) {
            array[i] = node.getValue();
            i++;
            node = node.getRight();
        }
        return array;
    }

    public static Node treeGenerate(int[] array) {
        Node root = new Node(array[0], null, null, 0);
        maxLengthOfNumber = (int) (Math.log10(array[0]) + 1);
        for (int i = 1; i < array.length; i++) {
            maxLengthOfNumber = (int) (Math.log10(array[i]) + 1);
            Node node = root;
            while (node != null) {
                if (node.getValue() <= array[i]) {
                    if (node.getRight() != null) {
                        node = node.getRight();
                    } else {
                        int level = node.getLevel() + 1;
                        node.setRight(new Node(array[i], node, Side.RIGHT, level));
                        if (maxLevel < level) maxLevel = level;
                        root = balanceSplayTree(node.getRight());
                        break;
                    }
                }
                if (node.getValue() > array[i]) {
                    if (node.getLeft() != null) {
                        node = node.getLeft();
                    } else {
                        int level = node.getLevel() + 1;
                        node.setLeft(new Node(array[i], node, Side.LEFT, level));
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
//      If X in the tree is at an even lower nesting level than 2, then to raise it, you need to
//      apply ZigZig or ZigZag (if you need to do this several times) to the parent up
//      the branch, which is at the 2 nesting level.
        while (node.getLevel() > 2) {
            Node parentNode = node.getParent();
            while (parentNode.getLevel() != 2) {
                parentNode = parentNode.getParent();
            }
            balanceSplayTree(parentNode);
        }
        if (node.getLevel() == 0) {
            return node;
        }
        if (node.getLevel() == 2) {
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

    //          P               X
    //      ┌───┴───┐  >>>  ┌───┴───┐
    //      X       C       A       P
    //  ┌───┴───┐               ┌───┴───┐
    //  A       B               B       C
    // or mirror it
    public static Node zig(Node node) {
        Side side = node.getSide();
        Node child = node.getChildBySide(getOtherSide(side));

        node.setChild(getOtherSide(side), node.getParent());
        node.getParent().setSide(getOtherSide(side));
        node.getParent().setChild(side, child);
        if (child != null) {
            child.setSide(side);
            child.setParent(node.getParent());
        }

        node.getParent().setParent(node);
        node.setParent(null);
        node.setLevel(0);
        node.setSide(null);
        updateLevelForAllChild(node);

        return node;
    }

    //              G
    //          ┌───┴───┐
    //          P       D                          X
    //      ┌───┴───┐                          ┌───┴───┐
    //      X       C          >>>             A       P
    //  ┌───┴───┐                                  ┌───┴───┐
    //  A       B                                  B       G
    //                                                 ┌───┴───┐
    //                                                 C       D
    // or mirror it
    public static Node zigZig(Node node) {
        Side side = node.getSide();

        Node child = node.getChildBySide(getOtherSide(side));
        Node nodeParent = node.getParent();
        Node parentChild = nodeParent.getChildBySide(getOtherSide(side));
        Node nodeParentParent = nodeParent.getParent();

        node.setChild(getOtherSide(side), nodeParent);
        nodeParent.setSide(getOtherSide(side));
        nodeParent.setChild(getOtherSide(side), nodeParentParent);
        nodeParentParent.setSide(getOtherSide(side));
        node.getParent().setChild(side, child);
        if (child != null) {
            child.setSide(side);
            child.setParent(nodeParent);
        }
        nodeParentParent.setChild(side, parentChild);
        if (parentChild != null) {
            parentChild.setSide(side);
            parentChild.setParent(nodeParentParent);
        }

        nodeParentParent.setParent(nodeParent);
        nodeParent.setParent(node);
        node.setParent(null);
        node.setLevel(0);
        node.setSide(null);
        updateLevelForAllChild(node);

        return node;
    }

    //              G
    //          ┌───┴───┐
    //          P       D                              X
    //      ┌───┴───┐                          ┌───────┴───────┐
    //      A       X          >>>             P               G
    //          ┌───┴───┐                  ┌───┴───┐       ┌───┴───┐
    //          B       C                  A       B       C       D
    // or mirror it
    public static Node zigZag(Node node) {
        Side side = node.getSide();

        Node nodeParent = node.getParent();
        Node nodeParentParent = nodeParent.getParent();

        Node childSideAsNode = node.getChildBySide(getOtherSide(side));
        Node childOtherSideAsNode = node.getChildBySide(side);

        node.setChild(getOtherSide(side), nodeParent);
        nodeParent.setSide(getOtherSide(side));
        node.setChild(side, nodeParentParent);
        nodeParentParent.setSide(side);
        nodeParent.setChild(side, childOtherSideAsNode);
        if (childOtherSideAsNode != null) {
            childOtherSideAsNode.setSide(side);
            childOtherSideAsNode.setParent(nodeParent);
        }
        nodeParentParent.setChild(getOtherSide(side), childSideAsNode);
        if (childSideAsNode != null) {
            childSideAsNode.setSide(getOtherSide(side));
            childSideAsNode.setParent(nodeParentParent);
        }

        nodeParentParent.setParent(node);
        nodeParent.setParent(node);
        node.setParent(null);
        node.setLevel(0);
        node.setSide(null);
        updateLevelForAllChild(node);

        return node;
    }

    private static void updateLevelForAllChild(Node root) {
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int level = node.getLevel();
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
                node.getLeft().setLevel(level + 1);
                if (maxLevel < level + 1) maxLevel = level + 1;
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
                node.getRight().setLevel(level + 1);
                if (maxLevel < level + 1) maxLevel = level + 1;
            }
        }
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
