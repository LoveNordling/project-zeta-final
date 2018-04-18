package org.primal.java.entity;

public class Zebra extends Animal{
    public Zebra(float x, float y, float stamina, float fullness){
        super(x, y, stamina, fullness);
        starvationRate = 1;
    }
}
