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

    public LivingEntity(float x, float y, Map map, float health) {
        super(x, y, map);
        this.color = new Color(0, 0, 0);
        this.health = health;
        this.maxHP = health;
    }

    public void updateShape() {
        if (this.isAnimal()) {
            float visualSize = Tile.getSize() / 4;
            ((Rectangle.Float) this.shape).setRect(getX() * Tile.getSize() - visualSize / 2, getY() * Tile.getSize() - visualSize / 2, visualSize, visualSize);
        }
    }

    public boolean isAnimal() {
        return false;
    }

    public boolean isPlant() {
        return false;
    }

    public Shape getShape() {
        updateShape();
        return this.shape;
    }

    public void simulate() {
    }

    public void performAction() {
    }

    public void heal() {
        health = maxHP;
    }

    public Color getColor() {
        return color;
    }
}
