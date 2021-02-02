package com.example.tree;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class NodeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer value;
    private Side side;
    private int level;
    private int coordX;
    private int coordY;
    private int parentCoordX;
    private int parentCoordY;

    public NodeTable() {

    }

    public NodeTable(Node node) {
        this.value = node.getValue();
        this.level = node.getLevel();
        this.side = node.getSide();
        this.coordX = node.getCoordX();
        this.coordY = node.getCoordY();
        if (node.getParent() != null) {
            this.parentCoordX = node.getParent().getCoordX();
            this.parentCoordY = node.getParent().getCoordY();
        } else {
            this.parentCoordX = coordX;
            this.parentCoordY = coordY;
        }
    }
}
