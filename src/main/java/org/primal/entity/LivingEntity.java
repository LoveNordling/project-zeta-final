package org.primal.entity;

import javafx.scene.shape.Shape;

public abstract class LivingEntity extends Entity {
    Shape shape;
    float health;
    float energySatisfaction;

    public LivingEntity(float x, float y, Shape shape, float health) {
        super(x, y);
        this.shape = shape;
        this.health = health;
    }

    public Shape getShape() {
        return shape;
    }

    public void performAction() {
    }
}
