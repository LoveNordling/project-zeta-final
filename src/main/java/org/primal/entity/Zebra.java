package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.map.Map;

import java.awt.*;
import java.util.LinkedList;

public class Zebra extends Herbivore {

    public Zebra(float x, float y, Map map, float stamina, float fullness, Graphics g) {
        super(x, y, map, 100, stamina, fullness);

        Behaviour foodBehaviour = new SearchFoodBehaviour(this, map);
        this.behaviours = new LinkedList<Behaviour>();
        this.behaviours.add(foodBehaviour);
        this.starvationRate = 1;
    }
}
