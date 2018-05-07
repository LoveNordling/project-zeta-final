package org.primal.entity;

import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.geom.Ellipse2D;

public class Tree extends Plant {

    public Tree(float x, float y, Map map) {
        super(x, y, map);
        this.color = new java.awt.Color(0, 138, 3);
        this.shape = new Ellipse2D.Float((this.getX() + 0.2f) * Tile.getSize(), (this.getY() + 0.2f) * Tile.getSize(), Tile.getSize() / 1.5f, Tile.getSize() / 1.5f);
    }
}
