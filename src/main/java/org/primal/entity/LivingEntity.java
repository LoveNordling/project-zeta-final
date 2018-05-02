package org.primal.entity;

import org.primal.tile.Tile;
import org.primal.map.Map;

import java.awt.*;

public abstract class LivingEntity extends Entity {
    protected Rectangle.Float shape;
    float health;
    float energySatisfaction;
    protected Color color;
    protected int id;

    public LivingEntity(float x, float y, float health, int id) {
        super(x, y);
        this.id = id;
        this.shape = shape;
        this.color = new Color(0, 0, 0);
        this.health = health;
    }

    public int getId() {
        return id;
    }

    public void updateShape() {
        this.shape.setRect((getPosition()[0] - 0.5) * Tile.getSize(), (getPosition()[1] - 0.5) * Tile.getSize(), Tile.getSize() / 8, Tile.getSize() / 8);
    }

    public Rectangle.Float getShape() {
        updateShape();
        return this.shape;
    }

    public void simulate(Map map) {}

    public void performAction(Map map) {}

    public Color getColor(){
        return color;
    }
}
