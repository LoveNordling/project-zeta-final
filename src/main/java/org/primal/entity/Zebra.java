package org.primal.entity;

public class Zebra extends Animal {
    public Zebra(float x, float y, float stamina, float fullness) {
        super(x, y, stamina, fullness);
        starvationRate = 1;
    }

    @Override
    public void eat(LivingEntity food) {

    }
}
