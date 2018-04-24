package org.primal.entity;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Lion extends Animal {
    public Lion(float x, float y, float stamina, float fullness) {
        super(x, y, stamina, fullness, new Circle(x, y, 2, Color.ORANGE));
    }

    @Override
    public void eat(LivingEntity food) {

    }
}
