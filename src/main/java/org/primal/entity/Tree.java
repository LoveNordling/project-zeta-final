package org.primal.entity;

import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.geom.Ellipse2D;

public class Tree extends Plant {
    public Tree(float x, float y, Map map) {
        super(x, y, map);
        this.color = new java.awt.Color(9, 255, 0);
        this.shape = new Ellipse2D.Float(this.getX() * Tile.getSize(), this.getY() * Tile.getSize(), Tile.getSize(), Tile.getSize());
    }
}
