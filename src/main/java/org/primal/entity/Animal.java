package org.primal.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.primal.behaviour.Behaviour;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends LivingEntity {
    int starvationRate = 1;
    float stamina;
    float fullness;
    private Graphics g;
    LinkedList<Behaviour> behaviours;

    public Animal(float x, float y, float health, float stamina, float fullness, Graphics g){
        // TODO: remove static x y below.
        super(x, y, health);
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
        draw(g);

        //this.updateShape();
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(java.awt.Color.BLACK);
        g2d.setPaint(new java.awt.Color(0, 0, 0));
        g2d.fill(new Rectangle2D.Double(getPosition()[0] * Tile.getSize(), getPosition()[1] * Tile.getSize(), 10,10));
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

    }

    public abstract void eat(LivingEntity food);

    public float getFullness() {
        return this.fullness;
    }

    public float getStamina() {
        return this.stamina;
    }
}
