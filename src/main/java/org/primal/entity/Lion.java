package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.behaviour.ChaseBehaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.*;
import java.util.LinkedList;

public class Lion extends Carnivore {

    /**
     * Creates a Lion object
     * Creates a new carnivore object
     * @param x = x-coordinate
     * @param y = y-coordinate
     * @param map = current Map
     * @param stamina = stamina points
     * @param fullness = fullness points
     */

    public Lion(float x, float y, Map map, float stamina, float fullness) {
        super(x, y, map, 100, stamina, fullness);
        Behaviour foodBehaviour = new SearchFoodBehaviour(this, map);

        this.behaviours.add(foodBehaviour);
        this.behaviours.add(new ChaseBehaviour(this, map));
        this.starvationRate = 1;
        this.color = new java.awt.Color(0, 0, 150);
        this.shape = new Rectangle.Float(this.getX() * Tile.getSize(), this.getY() * Tile.getSize(), Tile.getSize() / 8, Tile.getSize() / 8);
    }

    @Override
    public String toString() {
        return "Lion";
    }
}
