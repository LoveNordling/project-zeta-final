package org.primal.entity;

import org.primal.map.Map;

public abstract class Herbivore extends Animal {

    boolean chasedStatus = false;

    /**
     * Creates a herbivore object
     *
     * @param x        = x-coordinate
     * @param y        = y-coordinate
     * @param map      = current Map
     * @param health   = health points
     * @param stamina  = stamina points
     * @param fullness = fullness points
     */

    public Herbivore(float x, float y, Map map, float health, float stamina, float fullness) {
        super(x, y, map, health, stamina, fullness);

    }

    /**
     * Function for eating an object
     *
     * @param food = the food to be eaten
     */

    public void eat(LivingEntity food) {
        if (food.isPlant()) {
            this.fullness = 100;
            this.health = 100;
        }
    }

    /**
     * Simulation method, calls on the simulate function in its super class
     */

    public void simulate() {
        super.simulate();
    }

    /**
     * Checks if an animal is being chased by a carnivore
     *
     * @return true if chased, else false
     */

    public boolean isChased() {
        return this.chasedStatus;
    }

    /**
     * Method called when this animal starts being chased
     */

    public void startChasing() {
        this.chasedStatus = true;
    }

    /**
     * Method to see if the animal is a herbivore
     *
     * @return true
     */

    public boolean isHerbivore() {
        return true;
    }

    /**
     * Checks whether an animal is Alive
     * @return true if animal is alive, false if dead
     */
    public boolean isAlive() {
        return super.isAlive();
    }

    /**
     * Kills the animal setting it's alive status to false and removes it from the map.
     */

    public void kill() {
        super.die();
        map.getTile(this.getX(), this.getY()).removeLivingEntity(this);
    }

    /**
     * Starves the animal (used when health reaches 0)
     */
    public void starve() {
        kill();
        System.out.println(this + " was unable to find food and died.");
    }
}
