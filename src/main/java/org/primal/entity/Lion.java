package org.primal.entity;


import org.primal.behaviour.Behaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.map.Map;

import java.util.LinkedList;

public class Lion extends Animal {
    public Lion(float x, float y, float stamina, float fullness, Map map) {
        super(x, y, stamina, fullness);
        Behaviour foodBehaviour = new SearchFoodBehaviour(this, map);
        this.behaviours = new LinkedList<Behaviour>();
        this.behaviours.add(foodBehaviour);
        this.starvationRate = 1;
    }

    @Override
    public void eat(LivingEntity food) {

    }
}
