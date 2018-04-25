package org.primal.entity;

import org.primal.tile.Tile;

import java.awt.*;

public abstract class LivingEntity extends Entity {
    Rectangle.Double shape;
    float health;
    float energySatisfaction;
    protected Color color;

    public LivingEntity(float x, float y, float health) {
        super(x, y);
        this.shape = shape;
        this.color = new Color(0,0,0);
        this.health = health;
    }

    public void updateShape() {
        this.shape.setRect(getPosition()[0]*Tile.getSize(), getPosition()[1]*Tile.getSize(), Tile.getSize()/8, Tile.getSize()/8);
    }

    public Shape getShape() {
        updateShape();
        return this.shape;
    }

    public Color getColor(){
        return color;
    }

    public void performAction() {
    }
}
