package org.primal.entity;

import org.primal.behaviour.*;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.util.LinkedList;

public class Giraffe extends Herbivore {

    /**
     * Creates a Giraffe object
     * Creates a new herbivore object
     * @param x        = x-coordinate
     * @param y        = y-coordinate
     * @param map      = current Map
     * @param stamina  = stamina points
     * @param fullness = fullness points
     * @param thirst = thirst level
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
    }
    
    public String getType (){
        return "Giraffe";
    }
    public void breed(){
        Tile t = map.getTile(this.getX(), this.getY());
        map.spawnGiraffe(t);
    }

    @Override
    public String toString() {
        return "Giraffe #" + this.getId();
    }
}
