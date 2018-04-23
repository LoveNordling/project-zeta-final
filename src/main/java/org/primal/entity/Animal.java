package org.primal.entity;


import org.primal.behaviour.*;

import java.util.LinkedList;
import java.util.Random;
import org.primal.map.Map;

public abstract class Animal extends LivingEntity {
    protected LinkedList<Behaviour> behaviours;
    protected float stamina;
    protected float fullness; //0-100
    protected int starvationRate = 1;

    public Animal(float x, float y, float stamina, float fullness) {
        super(x, y);
        this.behaviours = behaviours;
        this.stamina = stamina;
        this.fullness = fullness;
    }

    public Animal(float x, float y) {
        this(x, y, 100, 100);
    }

    public void preformAction(Map map) {
        Behaviour best = behaviours.getFirst();
        for (Behaviour behaviour: behaviours) {
            behaviour.decide();
            best = best.getWeight() < behaviour.getWeight() ? behaviour : best;
        }
        best.act();
    }

    // Temporary function for random movement
    public void move() {
        Random rand = new Random();
        int n = rand.nextInt(3);
        if (n == 0) {
            position[0] += 1;
        } else if (n == 1) {
            position[0] -= 1;
        } else if (n == 2) {
            position[1] += 1;
        } else {
            position[1] -= 1;
        }
    }

    public abstract void eat(LivingEntity food);
}
