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
    private Character[] lastDirections = new Character[4];
    private float lengthUnit = 0.1f;

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

    public void move() {
        if (lastDirections[0] != lastDirections[1]) {
            stepInDir(lastDirections[0]);
        } else {
            randomDir();
        }
        printDirs();
        updateShape();
    }

    private void stepInDir(Character c) {
        if (c == 'E') {
            position[0] += lengthUnit;
            updateLastDir('E');
        } else if (c == 'W') {
            position[0] -= lengthUnit;
            updateLastDir('W');
        } else if (c == 'N') {
            position[1] += lengthUnit;
            updateLastDir('N');
        } else  {
            position[1] -= lengthUnit;
            updateLastDir('S');
        }
    }

    private void randomDir() {
        int n = ThreadLocalRandom.current().nextInt(0, 4);
        if (n == 0) {
            stepInDir('E');
        } else if (n == 1) {
            stepInDir('W');
        } else if (n == 2) {
            stepInDir('N');
        } else {
            stepInDir('S');
        }
    }

    private void updateLastDir(Character c) {
        for (int i = 0; i < lastDirections.length-1; i++) {
            lastDirections[i + 1] = lastDirections[i];
        }
        lastDirections[0] = c;
    }

    private void printDirs() {
        for (Character c : lastDirections) {
            System.out.print(c);
        }
        System.out.println();
    }

    public abstract void eat(LivingEntity food);

    public float getFullness() {
        return this.fullness;
    }

    public float getStamina() {
        return this.stamina;
    }
}
