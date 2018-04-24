package org.primal.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.primal.behaviour.Behaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.map.Map;

import java.util.LinkedList;

public class Giraffe extends Animal {
    public Giraffe(float x, float y, float stamina, float fullness, Map map) {
        super(x, y, 100, stamina, fullness, new Circle(x, y, 2, Color.YELLOW));
        Behaviour foodBehaviour = new SearchFoodBehaviour(this, map);
        this.behaviours = new LinkedList<>();
        this.behaviours.add(foodBehaviour);
        starvationRate = 1;
    }

    @Override
    public void eat(LivingEntity food) {

    }
}
