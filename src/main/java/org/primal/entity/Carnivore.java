package org.primal.entity;

import org.primal.map.Map;
import org.primal.tile.Tile;
import org.primal.map.Chunk;

import java.util.ArrayList;
import java.sql.SQLOutput;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Carnivore extends Animal {


    public Carnivore(float x, float y, Map map, float health, float stamina, float fullness) {
        super(x, y, map, health, fullness, stamina);
    }
    public void eat(LivingEntity food) {
        if (food instanceof Herbivore) {
            Tile tile = map.getTile(food.getX(), food.getY());
            tile.removeLivingEntity(food);
            this.stamina = getStamina() + 10;
        }
    }

    public void simulate() {
        super.simulate();
        //move();
    }

    public void chase(Herbivore target) {

        float dx = target.getX() - this.getX();
        float dy = target.getY() - this.getY();

        float newX = getX() + dx * 0.1f;
        float newY = getY() + dy * 0.1f;

        this.position.setLocation(newX, newY);
    }
}

