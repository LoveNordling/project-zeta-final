package org.primal.entity;

import javafx.scene.shape.Shape;

public abstract class LivingEntity extends Entity {
    private Shape shape;
    float health;
    float energySatisfaction;

    public LivingEntity(float x, float y) {
        super(x, y);
    }

    public Shape getShape() {
        return shape;
    }

    public void preformAction() {
    }
}
