package org.primal.java.entity;

public abstract class LivingEntity extends Entity {

    float health;
    float energySatisfaction;

    public LivingEntity(float x, float y) {
        super(x, y);
    }
}
