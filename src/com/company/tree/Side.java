package com.company.tree;

public enum Side {
    LEFT("left"),
    RIGHT("right");

    Side(String side) {
    }

    public static Side getOtherSide(Side side){
        if(side.equals(LEFT)){
            return RIGHT;
        }
        else{
            return LEFT;
        }
    }
}
