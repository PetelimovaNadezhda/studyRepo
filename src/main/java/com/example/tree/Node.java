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
    private int coordX;
    private int coordY;
    private NodeTable equalNodeTable;

    public Node(Integer value, Node parent) {
        this.parent = parent;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public void setChild(Side side, Node node) {
        if (side.equals(LEFT)) {
            this.setLeft(node);
        } else {
            this.setRight(node);
        }
    }

    public Node getChild(Side side) {
        if (side.equals(LEFT)) {
            return this.getLeft();
        } else {
            return this.getRight();
        }
    }

    public Node setParent(Node parent) {
        this.parent = parent;
        return this;
    }

    public Node setLeft(Node left) {
        this.left = left;
        return this.left;
    }

    public Node setRight(Node right) {
        this.right = right;
        return this.right;
    }

    public Node setSide(Side side) {
        this.side = side;
        return this;
    }

    public boolean hasChild() {
        return this.left != null || this.right != null;
    }

    public boolean hasLeftChild() {
        return this.left != null ;
    }

    public boolean hasRightChild() {
        return this.right != null;
    }
}
