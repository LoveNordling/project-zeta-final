package org.primal.entity;

public class Hyena extends Animal {
    public Hyena(float x, float y, float stamina, float fullness) {
        super(x, y, stamina, fullness);
        starvationRate = 1;
    }

    @Override
    public void eat(LivingEntity food) {

    }
}
