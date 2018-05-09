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
                Tile tile = map.getTile(food.getX(), food.getY());
                tile.removeLivingEntity(food);
                this.fullness = 100;
    }

    public void simulate() {
        super.simulate();
        //move();
    }
}

