package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.shape.Shape;

public abstract class Animal extends LivingEntity {
    int starvationRate = 1;
    float stamina;
    float fullness;
    private Graphics g;

    LinkedList<Behaviour> behaviours;

    public Animal(float x, float y, float health, float stamina, float fullness, Graphics g){
        // TODO: remove static x y below.
        super(x, y, health);
        this.shape = new Rectangle.Double(this.getPosition()[0] * Tile.getSize(), this.getPosition()[1] * Tile.getSize(), Tile.getSize() / 4, Tile.getSize() / 4);

        this.g = g;
        this.stamina = stamina;
        this.fullness = fullness;
        //this.shape.setOnMousePressed(click -> System.out.printf("Type: Animal %n Fullness: " + getFullness() + "%n Stamina: " + getStamina() + "%n"));
    }

    public Animal(float x, float y, Graphics g) {
        this(x, y, 100, 100, 100, g);
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

        int n = ThreadLocalRandom.current().nextInt(0, 3);
        if (n==0) {
            position[0] += 0.1;
        } else if (n == 1) {
            position[0] -= 0.1;
        } else if (n == 2) {
            position[1] += 0.1;
        } else {
            position[1] -= 0.1;
        }
        System.out.println("moved to"+ getPosition()[0]);
        shape.setRect(position[0]*Tile.getSize(), position[1]*Tile.getSize(), Tile.getSize()/8, Tile.getSize()/8);
    }

    public abstract void eat(LivingEntity food);

    public float getFullness() {
        return this.fullness;
    }

    public float getStamina() {
        return this.stamina;
    }
}
