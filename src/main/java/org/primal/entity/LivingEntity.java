package org.primal.entity;

import javafx.scene.shape.Shape;
import org.primal.tile.Tile;

public abstract class LivingEntity extends Entity {
    Shape shape;
    float health;
    float energySatisfaction;

    public LivingEntity(float x, float y, Shape shape, float health) {
        super(x, y);
        this.shape = shape;
        this.health = health;
    }

    public void updateShape() {
        this.shape.setTranslateX(position[0] * (Tile.getSize() -1) + Tile.getSize()/2);
        this.shape.setTranslateY(position[1] * (Tile.getSize() -1) + Tile.getSize()/2);
    }

    public Shape getShape() {
        updateShape();
        return this.shape;
    }

    public void performAction() {
    }
}
