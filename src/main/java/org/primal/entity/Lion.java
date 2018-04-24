package org.primal.entity;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.primal.behaviour.Behaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.map.Map;

import java.util.LinkedList;

public class Lion extends Animal {
    public Lion(float x, float y, float stamina, float fullness, Map map) {
        super(x, y, 100, stamina, fullness, new Circle(x, y, 2, Color.ORANGE));
        Behaviour foodBehaviour = new SearchFoodBehaviour(this, map);
        this.behaviours = new LinkedList<>();
        this.behaviours.add(foodBehaviour);
        this.starvationRate = 1;
    }

    public void eat(LivingEntity food) {

    }
}
