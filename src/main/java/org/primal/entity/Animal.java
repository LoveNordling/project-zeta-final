package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.tile.Tile;

import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends LivingEntity {
    float starvationRate = 0.001f;
    float stamina;
    float fullness;
    private Graphics g;

    LinkedList<Behaviour> behaviours;

    public Animal(float x, float y, float health, float stamina, float fullness) {
        // TODO: remove static x y below.
        super(x, y, health);

        this.shape = new Rectangle.Float(this.getPosition()[0] * Tile.getSize(), this.getPosition()[1] * Tile.getSize(), Tile.getSize() / 4, Tile.getSize() / 4);

        this.stamina = stamina;
        this.fullness = fullness;
        energySatisfaction = 100;
        //this.shape.setOnMousePressed(click -> System.out.printf("Type: Animal %n Fullness: " + getFullness() + "%n Stamina: " + getStamina() + "%n"));
    }

    public Animal(float x, float y) {
        this(x, y, 100, 100, 100);
    }

    private void updateStats() {
        if (stamina > 0 && fullness > 0) {
            stamina -= starvationRate;
            fullness -= starvationRate;
        } else if (energySatisfaction > 0) {
            energySatisfaction -= starvationRate;
        }
        if (energySatisfaction < 50 && health <= 0) {
            health -= starvationRate * 10;
        }
    }

    public void simulate() {
        super.simulate();
        Behaviour best = behaviours.getFirst();
        for (Behaviour behaviour : behaviours) {
            behaviour.decide();
            best = best.getWeight() < behaviour.getWeight() ? behaviour : best;
        }
        best.act();
        updateStats();
    }

    // Temporary function for random movement
    public void move() {
        int n = ThreadLocalRandom.current().nextInt(0, 4);
        if (n == 0) {
            position[0] += 0.1;
        } else if (n == 1) {
            position[0] -= 0.1;
        } else if (n == 2) {
            position[1] += 0.1;
        } else {
            position[1] -= 0.1;
        }
        updateShape();
    }


    public abstract void eat(LivingEntity food);

    public float getFullness() {
        return this.fullness;
    }

    public float getStamina() {
        return this.stamina;
    }
}
