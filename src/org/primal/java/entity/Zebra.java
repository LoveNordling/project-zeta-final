package org.primal.java.entity;

public class Zebra extends Animal{
    public Zebra(float x, float y, float stam, float fullns){
        super(x, y, stam, fullns);
        starvationRate = 1;
    }
}
