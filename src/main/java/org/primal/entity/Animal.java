package org.primal.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.primal.behaviour.Behaviour;

import java.util.LinkedList;
import java.util.Random;

public abstract class Animal extends LivingEntity {
    protected LinkedList<Behaviour> behaviours;
    protected float stamina;
    protected float fullness; //0-100
    protected int starvationRate = 1;
    private Circle shape;

    public Animal(float x, float y, float stamina, float fullness) {
        super(x, y);
        this.stamina = stamina;
        this.fullness = fullness;
        this.shape = new Circle(x * 20, y * 20, 2, Color.GREEN);
    }

    public Animal(float x, float y) {
        this(x, y, 100, 100);
    }

    public Circle getShape() {
        return shape;
    }

    public void preformAction() {
        move();
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
