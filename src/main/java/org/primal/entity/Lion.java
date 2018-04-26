package org.primal.entity;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.primal.behaviour.Behaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.*;
import java.util.LinkedList;

public class Lion extends Animal {
    public Lion(float x, float y, float stamina, float fullness, Map map) {
        super(x, y, 100, stamina, fullness);
        Behaviour foodBehaviour = new SearchFoodBehaviour(this, map);
        this.behaviours = new LinkedList<>();
        this.behaviours.add(foodBehaviour);
        this.starvationRate = 1;
        this.color = new java.awt.Color(0,0,150);
        this.shape = new Rectangle.Float(this.getPosition()[0] * Tile.getSize(), this.getPosition()[1] * Tile.getSize(), Tile.getSize() / 2, Tile.getSize() / 2);
    }

    public void eat(LivingEntity food) {

    }


}
