package org.primal.java.entity;

public class Hyena extends Animal{
    public Hyena(float x, float y, float stamina, float fullness){
        super(x, y, stamina, fullness);
        starvationRate = 1;
    }
}
