package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.map.Map;

import java.util.LinkedList;

public class Hyena extends Animal {
    public Hyena(float x, float y, float stamina, float fullness, Map map) {
        super(x, y, 100, stamina, fullness);
        Behaviour foodBehaviour = new SearchFoodBehaviour(this, map);
        this.behaviours = new LinkedList<>();
        this.behaviours.add(foodBehaviour);
        starvationRate = 1;
    }

    @Override
    public void eat(LivingEntity food) {

    }
}
