package org.primal.entity;

import org.primal.map.Map;
import org.primal.tile.Tile;

public abstract class Carnivore extends Animal {

    /**
     * Creates a new carnivore object
     *
     * @param x        = x-coordinate
     * @param y        = y-coordinate
     * @param map      = current Map
     * @param health   = health points
     * @param stamina  = stamina points
     * @param fullness = fullness points
     */

    public Carnivore(float x, float y, Map map, float health, float stamina, float fullness) {
        super(x, y, map, health, fullness, stamina);
    }

    /**
     * Function for eating another animal
     *
     * @param food = animal to be eaten
     */

    public void eat(LivingEntity food) {
        Tile tile = map.getTile(food.getX(), food.getY());
        tile.removeLivingEntity(food);
        this.fullness = 100;
    }

    /**
     * Simulation method, calls on the simulate function in its super class
     */
    public void simulate() {
        super.simulate();
        //move();
    }
}

