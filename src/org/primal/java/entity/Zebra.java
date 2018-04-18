package org.primal.java.entity;

public abstract class Zebra extends Animal {
    public Zebra(float x, float y, float stamina, float fullness) {
        super(x, y, stamina, fullness);
        starvationRate = 1;
    }
}
