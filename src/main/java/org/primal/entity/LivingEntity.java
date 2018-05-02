package org.primal.entity;

import org.primal.tile.Tile;
import org.primal.map.Map;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public abstract class LivingEntity extends Entity {
    protected Shape shape;
    float health;
    float energySatisfaction;
    protected Color color;

    public LivingEntity(float x, float y, Map map, float health) {
        super(x, y, map);
        this.color = new Color(0, 0, 0);
        this.health = health;
    }

    public void updateShape() {
        if (this instanceof Animal) {
            ((Rectangle.Float) this.shape).setRect((getX() - 0.5) * Tile.getSize(), (getY() - 0.5) * Tile.getSize(), Tile.getSize() / 8, Tile.getSize() / 8);
        }
    }

    public Shape getShape() {
        updateShape();
        return this.shape;
    }

    public void simulate() {}

    public void performAction() {}

    public Color getColor(){
        return color;
    }
}
