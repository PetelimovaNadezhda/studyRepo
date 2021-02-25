package com.example.tree;

import lombok.Getter;
import lombok.Setter;

import static com.example.tree.Side.LEFT;

@Getter
@Setter
public class Node {

    private final Integer value;
    private Node parent;
    private Node left;
    private Node right;
    private Side side;
    private int level;
    private int coordinateX;
    private int coordinateY;
    private NodeTable equalNodeTable;

    public Node(int value, Node parent, Side side, int level) {
        this.parent = parent;
        this.value = value;
        this.left = null;
        this.right = null;
        this.side = side;
        this.level = level;
    }

    public void setChild(Side side, Node node) {
        if (side.equals(LEFT)) {
            this.left = node;
        } else {
            this.right = node;
        }
    }

    public Node getChildBySide(Side side) {
        if (side.equals(LEFT)) {
            return this.getLeft();
        } else {
            return this.getRight();
        }
    }

    public boolean isHasChild() {
        return this.left != null || this.right != null;
    }

    public boolean isHasLeftChild() {
        return this.left != null;
    }

    public boolean isHasRightChild() {
        return this.right != null;
    }
}
