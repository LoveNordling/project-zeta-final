package org.primal.entity;

import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.ThreadLocalRandom;

public class UmbrellaTree extends Plant {

    /**
     * Creates a Umbrella tree object.
     *
     * @param x    = the x-coordinate
     * @param y    = the y-coordinate
     * @param map  = the current Map
     * @param size = starting size of the Shape
     */

    public UmbrellaTree(float x, float y, Map map, float size) {
        super(x, y, map);
        this.color = new Color(ThreadLocalRandom.current().nextInt(30, 100), 158, 9, 80);
        float visualSize = Tile.TILE_SIZE * size;
        this.shape = new Ellipse2D.Float(this.getX() * Tile.TILE_SIZE - visualSize / 2, this.getY() * Tile.TILE_SIZE - visualSize / 2, visualSize, visualSize);
    }

    @Override
    public String toString() {
        return "UmbrellaTree";
    }
}
