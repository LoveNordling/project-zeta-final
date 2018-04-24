package org.primal.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Hyena extends Animal {
    public Hyena(float x, float y, float stamina, float fullness) {
        super(x, y, stamina, fullness, new Circle(x, y, 2, Color.BROWN));
        starvationRate = 1;
    }

    @Override
    public void eat(LivingEntity food) {

    }
}
