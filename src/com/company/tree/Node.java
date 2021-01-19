package com.company.tree;

import static com.company.tree.Side.LEFT;
import static com.company.tree.Side.RIGHT;

public class Node {
    private final Integer value;
    private Node parent;
    private Node left;
    private Node right;
    private Side side;
    private int level;
    private boolean mark;

    public Node(Integer value, Node parent) {
        this.parent = parent;
        this.value = value;
        this.left = null;
        this.right = null;
        this.mark = false;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public int getLevel() {
        return level;
    }

    public Node setLevel(int level) {
        this.level = level;
        return this;
    }

    public Node setChild(Side side, Node node) {
        if (side.equals(LEFT)) {
            this.setLeft(node);
        } else {
            this.setRight(node);
        }
        return this;
    }

    public Node getChild(Side side) {
        if (side.equals(LEFT)) {
            return this.getLeft();
        } else {
            return this.getRight();
        }
    }

    public Node getParent() {
        return parent;
    }

    public Node setParent(Node parent) {
        this.parent = parent;
        return this;
    }

    public Node getLeft() {
        return left;
    }

    public Node setLeft(Node left) {
        this.left = left;
        return this.left;
    }

    public Node getRight() {
        return right;
    }

    public Node setRight(Node right) {
        this.right = right;
        return this.right;
    }

    public Integer getValue() {
        return value;
    }

    public Side getSide() {
        return side;
    }

    public Node setSide(Side side) {
        this.side = side;
        return this;
    }

    public boolean hasChild() {
        return this.left != null || this.right != null;
    }
}
