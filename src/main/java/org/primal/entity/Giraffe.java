package org.primal.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Giraffe extends Animal {
    public Giraffe(float x, float y, float stamina, float fullness) {
        super(x, y, stamina, fullness, new Circle(x, y, 2, Color.YELLOW));
        starvationRate = 1;
    }

    @Override
    public void eat(LivingEntity food) {

    }
}
