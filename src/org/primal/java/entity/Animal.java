package org.primal.java.entity;

import org.primal.java.behaviour.Behaviour;

import java.util.LinkedList;

public abstract class Animal extends LivingEntity {
    protected LinkedList<Behaviour> behaviours;
    protected float stamina;
    protected float fullness; //0-100

    public Animal(float x, float y, float stam, float fullns) {
        super(x, y);
        this.stamina = stam;
        this.fullness = fullns;
    }

    public Animal(float x, float y) {
        this(x, y, 100, 100);

    }

    public abstract void eat(LivingEntity food);
}
