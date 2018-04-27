package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.map.Map;

import java.util.LinkedList;

public class Giraffe extends Animal {
    public Giraffe(float x, float y, float stamina, float fullness, Map map, int id) {
        super(x, y, 100, stamina, fullness, id);
        Behaviour foodBehaviour = new SearchFoodBehaviour(this, map);
        this.behaviours = new LinkedList<>();
        this.behaviours.add(foodBehaviour);
        starvationRate = 1;
        this.color = new java.awt.Color(0, 150, 150);
    }

    @Override
    public void eat(LivingEntity food) {

    }
}
