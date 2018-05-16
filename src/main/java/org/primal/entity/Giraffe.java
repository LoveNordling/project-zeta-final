package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.behaviour.BreedingBehaviour;
import org.primal.behaviour.FeedingBehaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.behaviour.ThirstBehaviour;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.util.LinkedList;

public class Giraffe extends Herbivore {

    /**
     * Creates a Giraffe object
     * Creates a new herbivore object
     *
     * @param x        = x-coordinate
     * @param y        = y-coordinate
     * @param map      = current Map
     * @param stamina  = stamina points
     * @param fullness = fullness points
     * @param thirst   = thirst level
     */

    public Giraffe(float x, float y, Map map, double stamina, double fullness, double thirst) {
        super(x, y, map, 100, stamina, fullness, thirst);
        Behaviour breedBehaviour = new BreedingBehaviour(this, map);
        Behaviour searchBehaviour = new SearchFoodBehaviour(this, map);
        Behaviour foodBehaviour = new FeedingBehaviour(this, map);
        Behaviour thirstBehaviour = new ThirstBehaviour(this, map);

        this.behaviours = new LinkedList<>();

        this.behaviours.add(searchBehaviour);
        this.behaviours.add(foodBehaviour);
        this.behaviours.add(breedBehaviour);
        this.behaviours.add(thirstBehaviour);

        this.color = new java.awt.Color(255, 251, 0);
        this.shapeSize = Tile.getSize() / 2.5f;

        speed = 0.02;
    }

    /**
     * getType gets the type of the animal
     *
     * @return a string representing the type of the animal
     */
    public String getType() {
        return "Giraffe";
    }

    /**
     * breed creates a new hyena at this hyena's tile
     */
    public void breed() {
        Tile t = map.getTile(this.getX(), this.getY());
        map.spawnGiraffe(t);
    }

    @Override
    public String toString() {
        return "Giraffe #" + this.getId();
    }
}
