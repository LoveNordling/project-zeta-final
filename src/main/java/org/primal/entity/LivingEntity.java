package org.primal.entity;

import javafx.scene.shape.Shape;
import org.primal.tile.Tile;

public abstract class LivingEntity extends Entity {
    float health;
    float energySatisfaction;

    public LivingEntity(float x, float y) {
        super(x, y);
    }

    public void updateShape() {
        this.shape.setTranslateX(position[0] * Tile.getSize() - position[0]);
        this.shape.setTranslateY(position[1] * Tile.getSize() - position[1]);
    }

    public Shape getShape() {
        updateShape();
        return this.shape;
    }

    public void performAction() {
    }
}
