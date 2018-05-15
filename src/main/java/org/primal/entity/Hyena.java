package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.behaviour.ChaseBehaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.behaviour.ThirstBehaviour;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.util.LinkedList;

public class Hyena extends Carnivore {

    /**
     * Creates a Hyena object
     * Creates a new carnivore object
     * @param x        = x-coordinate
     * @param y        = y-coordinate
     * @param map      = current Map
     * @param stamina  = stamina points
     * @param fullness = fullness points
     * @param thirst = thirst level
     */

    public Hyena(float x, float y, Map map, double stamina, double fullness, double thirst) {
        super(x, y, map, 100, stamina, fullness, thirst);
        Behaviour foodBehaviour = new ChaseBehaviour(this, map);
        Behaviour searchBehaviour = new SearchFoodBehaviour(this, map);
        Behaviour thirstBehaviour = new ThirstBehaviour(this, map);

        this.behaviours = new LinkedList<>();

        this.behaviours.add(searchBehaviour);
        this.behaviours.add(thirstBehaviour);
        this.behaviours.add(foodBehaviour);
        this.color = new java.awt.Color(108, 63, 22);
        this.shapeSize = Tile.getSize() / 4;
    }

    @Override
    public String toString() {
        return "Hyena #" + getId();
    }
}
