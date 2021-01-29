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

    public NodeTable() {

    }

    public NodeTable(Node node) {
        this.value = node.getValue();
        this.level = node.getLevel();
        this.side = node.getSide();
    }
}
