package org.primal.entity;

import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.geom.Ellipse2D;
import java.util.concurrent.ThreadLocalRandom;

public class Tree extends Plant {

    /**
     * Creates a tree object.
     * @param x = the x-coordinate
     * @param y = the y-coordinate
     * @param map = the current Map
     * @param size = starting size of the Shape
     */

    public Tree(float x, float y, Map map, float size) {
        super(x, y, map);
        this.color = new java.awt.Color(ThreadLocalRandom.current().nextInt(30, 100), 158, 9);
        float visualSize = Tile.getSize()*size;
        this.shape = new Ellipse2D.Float(this.getX() * Tile.getSize() - visualSize/2, this.getY() * Tile.getSize() - visualSize/2, visualSize, visualSize);
    }
}
