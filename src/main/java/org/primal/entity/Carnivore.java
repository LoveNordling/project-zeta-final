package org.primal.entity;

import org.primal.map.Map;
import org.primal.tile.Tile;

public abstract class Carnivore extends Animal {

    /**
     * Creates a new carnivore object
     * @param x        = x-coordinate
     * @param y        = y-coordinate
     * @param map      = current Map
     * @param health = health points
     * @param stamina  = stamina points
     * @param fullness = fullness points
     * @param thirst = thirst level
     */

    public Carnivore(float x, float y, Map map, double health, double stamina, double fullness, double thirst) {
        super(x, y, map, health, fullness, stamina, thirst);
    }

    /**
     * Function for eating another animal
     *
     * @param food = animal to be eaten
     */

    public void eat(LivingEntity food) {
        if (food instanceof Herbivore) {
            if (((Herbivore) food).isAlive()) {
                ((Herbivore) food).kill();
                //System.out.println(food.toString() + " was just eaten!!!");
                if (this.fullness < 100) this.fullness += 50;
                if (this.health < 100) this.health += 10;
            }
        }
    }

    /**
     * Simulation method, calls on the simulate function in its super class
     */
    public void simulate() {
        super.simulate();
        //move();
    }

    /**
     * Kills the animal, removing it from the map.
     */
    public void kill() {
        this.die();
        map.getTile(getX(), getY()).removeLivingEntity(this);
    }

    /**
     * Starves the animal (used when health reaches 0)
     */

    public void starve() {
        kill();
        //System.out.println(this + " just starved to death!");
    }

    /**
     * Method to see if the animal is a Carnivore
     *
     * @return true
     */
    public boolean isCarnivore() {
        return true;
    }
}

