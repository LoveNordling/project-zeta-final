package org.primal.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.primal.behaviour.Behaviour;
import org.primal.map.Map;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends LivingEntity {
    LinkedList<Behaviour> behaviours;
    int starvationRate = 1;
    private float stamina;
    private float fullness; //0-100
    private Shape shape;

    public Animal(float x, float y, float stamina, float fullness, Shape shape) {
        super(60, 60);
        this.behaviours = behaviours;
        this.stamina = stamina;
        this.fullness = fullness;
        this.shape = shape;
    }

    public Animal(float x, float y) {
        this(x, y, 100, 100, new Circle(x, y, 2, Color.GREEN));
    }

    public Shape getShape() {
        return shape;
    }

    public void performAction(Map map) {
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

        this.shape.setTranslateX(position[0] * 5);
        this.shape.setTranslateY(position[1] * 5);
    }

    public abstract void eat(LivingEntity food);
}
