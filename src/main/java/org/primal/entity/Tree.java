package org.primal.entity;

import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.geom.Ellipse2D;
import java.util.concurrent.ThreadLocalRandom;

public class Tree extends Plant {

    public Tree(float x, float y, Map map, float size) {
        super(x, y, map);
        this.color = new java.awt.Color(ThreadLocalRandom.current().nextInt(30, 80), 158, 9);
        this.shape = new Ellipse2D.Float(this.getX() * Tile.getSize() - Tile.getSize()*size, this.getY() * Tile.getSize() - Tile.getSize()*size, Tile.getSize()*size, Tile.getSize()*size);
    }
}
