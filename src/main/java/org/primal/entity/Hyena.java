package org.primal.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.primal.behaviour.Behaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.map.Map;

import java.util.LinkedList;

public class Hyena extends Animal {
    public Hyena(float x, float y, float stamina, float fullness, Map map) {
        super(x, y, stamina, fullness, new Circle(x, y, 2, Color.BROWN));
        Behaviour foodBehaviour = new SearchFoodBehaviour(this, map);
        this.behaviours = new LinkedList<>();
        this.behaviours.add(foodBehaviour);
        starvationRate = 1;
    }

    @Override
    public void eat(LivingEntity food) {

    }
}
