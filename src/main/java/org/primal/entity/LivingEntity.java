package org.primal.entity;

import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.*;

public abstract class LivingEntity extends Entity {

    protected Shape shape;
    protected Color color;
    float health;
    private float maxHP;
    float energySatisfaction;

    /**
     * Creates a living entity. Classes such as Animals extends this class.
     * @param x = the x-coordinate
     * @param y = the y-coordinate
     * @param map = the current Map
     * @param health = starting health points
     */

    public LivingEntity(float x, float y, Map map, float health) {
        super(x, y, map);
        this.color = new Color(0, 0, 0);
        this.health = health;
        this.maxHP = health;
    }

    /**
     * Updates the shape of the object. Shapes are assumed to be rectangles.
     */

    public void updateShape() {
        if (this.isAnimal()) {
            float visualSize = Tile.getSize() / 4;
            ((Rectangle.Float) this.shape).setRect(getX() * Tile.getSize() - visualSize / 2, getY() * Tile.getSize() - visualSize / 2, visualSize, visualSize);
        }
    }

    /**
     * Checks whether an object is an animal. Should be overridden.
     * @return false
     */

    public boolean isAnimal() {
        return false;
    }

    /**
     * Checks whether an object is a plant. Should be overridden.
     * @return false
     */

    public boolean isPlant() {
        return false;
    }

    /**
     * Returns the objects shape.
     * @return Shape The objects shape.
     */

    public Shape getShape() {
        updateShape();
        return this.shape;
    }

    /**
     * Simulation function, used when simulating an object.
     */

    public void simulate() {
    }

    /**
     * Sets the health of the object to max capacity.
     */

    public void heal() {
        health = maxHP;
    }

    /**
     * Returns the color of the object's shape.
     * @return Color The color of the object's shape.
     */

    public Color getColor() {
        return color;
    }
}
