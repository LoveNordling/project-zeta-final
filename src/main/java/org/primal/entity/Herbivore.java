package org.primal.entity;

import org.primal.map.Map;

public abstract class Herbivore extends Animal {
    boolean chasedStatus = false;

    public Herbivore(float x, float y, Map map, float health, float stamina, float fullness) {
        super(x, y, map, health, stamina, fullness);

    }

    public void eat(Plant food) {
        map.getTile(food.getX(), food.getY()).removeLivingEntity(food);
        this.stamina = 100;
        //EAT FOOD
    }

    public void simulate() {

    }

    public boolean isChased() {
        return this.chasedStatus;
    }

    public void startChasing() {
        this.chasedStatus  = true;
    }
}
