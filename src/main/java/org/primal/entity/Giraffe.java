package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.behaviour.PackBehaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.map.Map;

import java.util.LinkedList;

public class Giraffe extends Herbivore {

    /**
     * Creates a Giraffe object
     * Creates a new carnivore object
     * @param x = x-coordinate
     * @param y = y-coordinate
     * @param map = current Map
     * @param stamina = stamina points
     * @param fullness = fullness points
     */

    public Giraffe(float x, float y, Map map, float stamina, float fullness) {
        super(x, y, map, 100, stamina, fullness);
        Behaviour foodBehaviour = new SearchFoodBehaviour(this, map);
        this.behaviours.add(foodBehaviour);

        starvationRate = 1;
        this.color = new java.awt.Color(171, 56, 190);
    }

}
