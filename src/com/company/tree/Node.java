package com.company.tree;

public class Node {
    Node parent;
    Node left;
    Node right;
    int value;
    public Node() {

    }

    public Node(int value, Node parent) {
        this.parent = parent;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public Node getParent() {
        return parent;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getValue() {
        return value;
    }
}
