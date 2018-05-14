package org.primal.entity;

import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.ThreadLocalRandom;

public class MankettiTree extends Plant {

    /**
     * Creates a Manketti tree object.
     *
     * @param x    = the x-coordinate
     * @param y    = the y-coordinate
     * @param map  = the current Map
     * @param size = starting size of the Shape
     */

    public MankettiTree(float x, float y, Map map, float size) {
        super(x, y, map);
        this.color = new Color(ThreadLocalRandom.current().nextInt(30, 100), 180, 9);
        float visualSize = Tile.getSize() * size;
        this.shape = new Ellipse2D.Float(this.getX() * Tile.getSize() - visualSize / 2, this.getY() * Tile.getSize() - visualSize / 2, visualSize, visualSize);
    }

    @Override
    public String toString() {
        return "MankettiTree";
    }
}
