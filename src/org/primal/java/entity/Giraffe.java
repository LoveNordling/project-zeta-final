package org.primal.java.entity;

public class Giraffe extends Animal{
    public Giraffe(float x, float y, float stamina, float fullness){
        super(x, y, stamina, fullness);
        starvationRate = 1;
    }
}
