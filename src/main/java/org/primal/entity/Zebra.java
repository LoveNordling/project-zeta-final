package org.primal.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.primal.behaviour.Behaviour;
import org.primal.behaviour.SearchFoodBehaviour;
import org.primal.map.Map;

import java.awt.*;
import java.util.LinkedList;


public class Zebra extends Animal {

    public Zebra(float x, float y, float stamina, float fullness, Map map, Graphics g) {
        super(x, y, 100, stamina, fullness);

        Behaviour foodBehaviour = new SearchFoodBehaviour(this, map);
        this.behaviours = new LinkedList<Behaviour>();
        this.behaviours.add(foodBehaviour);
        this.starvationRate = 1;
    }

    @Override
    public void eat(LivingEntity food) {

    }
}
