package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.behaviour.BreedingBehaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.util.LinkedList;

public class Zebra extends Herbivore {

    /**
     * Creates a Zebra object
     * Creates a new carnivore object
     *
     * @param x        = x-coordinate
     * @param y        = y-coordinate
     * @param map      = current Map
     * @param stamina  = stamina points
     * @param fullness = fullness points
     */

    public Zebra(float x, float y, Map map, float stamina, float fullness) {
        super(x, y, map, 100, stamina, fullness);
        Behaviour foodBehaviour = new SearchFoodBehaviour(this, map);
        Behaviour breedBehaviour = new BreedingBehaviour(this, map);
        this.behaviours = new LinkedList<>();
        this.behaviours.add(foodBehaviour);
        this.behaviours.add(breedBehaviour);
    }
    public String getType (){
        return "Zebra";
    }
    public void breed(){
        Tile t = map.getTile(this.getX(), this.getY());
        map.spawnZebra(t);
    }

    @Override
    public String toString() {
        return "Zebra #" + getId() ;
    }
}
