package org.primal.entity;

import javafx.scene.shape.Shape;

public abstract class LivingEntity extends Entity {
    float health;
    float energySatisfaction;
    private Shape shape;

    public LivingEntity(float x, float y) {
        super(x, y);
    }

    public Shape getShape() {
        return shape;
    }

    public void performAction() {
    }
}
