package org.primal.entity;

import java.util.LinkedList;
import org.primal.behaviour.*;
import org.primal.map.Map;


public class Zebra extends Animal {
    public Zebra(float x, float y, float stamina, float fullness, Map map) {
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
