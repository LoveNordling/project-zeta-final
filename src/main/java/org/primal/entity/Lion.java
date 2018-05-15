package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.behaviour.ChaseBehaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.behaviour.ThirstBehaviour;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.util.LinkedList;

public class Lion extends Carnivore {

    /**
     * Creates a Lion object
     * Creates a new carnivore object
     * @param x        = x-coordinate
     * @param y        = y-coordinate
     * @param map      = current Map
     * @param stamina  = stamina points
     * @param fullness = fullness points
     * @param thirst = thirst level
     */

    public Lion(float x, float y, Map map, double stamina, double fullness, double thirst) {
        super(x, y, map, 100, stamina, fullness, thirst);
        Behaviour foodBehaviour = new SearchFoodBehaviour(this, map);
        Behaviour thirstBehaviour = new ThirstBehaviour(this, map);
        this.behaviours = new LinkedList<>();

        this.behaviours.add(foodBehaviour);
        this.behaviours.add(thirstBehaviour);
        this.behaviours.add(new ChaseBehaviour(this, map));
        this.color = new java.awt.Color(183, 137, 47);
        this.shapeSize = Tile.getSize() / 3;
    }

    @Override
    public String toString() {
        return "Lion #" + getId();
    }
}
