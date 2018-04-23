package org.primal.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.primal.behaviour.Behaviour;
import org.primal.map.Map;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends LivingEntity {
    LinkedList<Behaviour> behaviours;
    int starvationRate = 1;
    private float stamina;
    private float fullness; //0-100
    private Circle shape;

    public Animal(float x, float y, float stamina, float fullness) {
        super(60, 60);
        this.behaviours = behaviours;
        this.stamina = stamina;
        this.fullness = fullness;
        this.shape = new Circle(x * 5, y * 5, 2, Color.GREEN);
    }

    public Animal(float x, float y) {
        this(x, y, 100, 100);
    }

    public Circle getShape() {
        return shape;
    }

    public void preformAction(Map map) {
        Behaviour best = behaviours.getFirst();
        for (Behaviour behaviour : behaviours) {
            behaviour.decide();
            best = best.getWeight() < behaviour.getWeight() ? behaviour : best;
        }
        best.act();
    }

    // Temporary function for random movement
    public void move() {
        int n = ThreadLocalRandom.current().nextInt(0, 4);
        if (n == 0) {
            position[0] += 1;
        } else if (n == 1) {
            position[0] -= 1;
        } else if (n == 2) {
            position[1] += 1;
        } else {
            position[1] -= 1;
        }

        this.shape.setCenterX(position[0] * 5);
        this.shape.setCenterY(position[1] * 5);
    }

    public abstract void eat(LivingEntity food);
}
