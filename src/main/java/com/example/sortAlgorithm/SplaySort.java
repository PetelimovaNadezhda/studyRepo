package com.example.sortAlgorithm;

import com.example.tree.Node;
import com.example.tree.Side;

import static com.example.tree.Side.getOtherSide;
import static com.example.tree.TreePrint.buildAndPrintTreeToConsole;

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
        Node root = new Node(array[0], null);
        maxLengthOfNumber = (int) (Math.log10(array[0]) + 1);
        root.setLevel(0);
        for (int i = 1; i < array.length; i++) {
            maxLengthOfNumber = (int) (Math.log10(array[i]) + 1);
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
        //If X in the tree is even at a lower nesting level, then to raise it, you need to apply ZigZig or ZigZag
        while (node.getParent() != null &&
                node.getParent().getParent() != null &&
                node.getParent().getParent().getParent() != null) {
            balanceSplayTree(node.getParent());
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

    //          P               X
    //      ┌───┴───┐  >>>  ┌───┴───┐
    //      X       C       A       P
    //  ┌───┴───┐               ┌───┴───┐
    //  A       B               B       C
    // or mirror it
    public static Node zig(Node node) {
        Side side = node.getSide();
        Node child = node.getChild(getOtherSide(side));

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
//        updateLevelForAllChild(node);

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

        Node child = node.getChild(getOtherSide(side));
        Node parentChild = node.getParent().getChild(getOtherSide(side));

        node.setChild(getOtherSide(side), node.getParent());
        node.getParent().setSide(getOtherSide(side));
        node.getParent().setChild(getOtherSide(side), node.getParent().getParent());
        node.getParent().getParent().setSide(getOtherSide(side));
        node.getParent().setChild(side, child);
        if (child != null) {
            child.setSide(side);
            child.setParent(node.getParent());
        }
        node.getParent().getParent().setChild(side, parentChild);
        if (parentChild != null) {
            parentChild.setSide(side);
            parentChild.setParent(node.getParent().getParent());
        }

        node.getParent().getParent().setParent(node.getParent());
        node.getParent().setParent(node);
        node.setParent(null);
        node.setLevel(0);
        node.setSide(null);
//        updateLevelForAllChild(node);

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

        Node childSideAsNode = node.getChild(getOtherSide(side));
        Node childOtherSideAsNode = node.getChild(side);
        node.setChild(getOtherSide(side), node.getParent());
        node.getParent().setSide(getOtherSide(side));
        node.setChild(side, node.getParent().getParent());
        node.getParent().getParent().setSide(side);
        node.getParent().setChild(side, childOtherSideAsNode);
        if (childOtherSideAsNode != null) {
            childOtherSideAsNode.setSide(side);
            childOtherSideAsNode.setParent(node.getParent());
        }
        node.getParent().getParent().setChild(getOtherSide(side), childSideAsNode);
        if (childSideAsNode != null) {
            childSideAsNode.setSide(getOtherSide(side));
            childSideAsNode.setParent(node.getParent().getParent());
        }

        node.getParent().getParent().setParent(node);
        node.getParent().setParent(node);
        node.setParent(null);
        node.setLevel(0);
        node.setSide(null);
//        updateLevelForAllChild(node);

        return node;
    }

    //need for console and web output
//    private static void updateLevelForAllChild(Node root) {
//        LinkedList<Node> queue = new LinkedList<>();
//        queue.add(root);
//        while (!queue.isEmpty()) {
//            Node node = queue.poll();
//            int level = node.getLevel();
//            if (node.getLeft() != null) {
//                queue.add(node.getLeft());
//                node.getLeft().setLevel(level + 1);
//                if (maxLevel < level + 1) maxLevel = level + 1;
//            }
//            if (node.getRight() != null) {
//                queue.add(node.getRight());
//                node.getRight().setLevel(level + 1);
//                if (maxLevel < level + 1) maxLevel = level + 1;
//            }
//        }
//    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getMaxLengthOfNumber() {
        return maxLengthOfNumber;
    }
}
