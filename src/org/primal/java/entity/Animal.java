package org.primal.java.entity;

import org.primal.java.behaviour.Behaviour;

import java.util.LinkedList;

public abstract class Animal extends LivingEntity {
    protected LinkedList<Behaviour> behaviours;
    protected float stamina;
    protected float fullness; //0-100
    protected int starvationRate = 1;

    public Animal(float x, float y, float stamina, float fullness){
        super(x,y);
        this.stamina = stamina;
        this.fullness = fullness;
    }

    public Animal(float x, float y) {
        this(x, y, 100, 100);

    }

    public abstract void eat(LivingEntity food);
}
