package org.primal.tile;

import org.primal.SimObject;
import org.primal.entity.LivingEntity;
import org.primal.map.Map;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Tile extends SimObject {

    Color[][] colors = new Color[3][3];
    private static int size = 30;
    private ConcurrentLinkedQueue<LivingEntity> livingEntities;

    /**
     * Creates a new tile object
     *
     * @param x   = x-coordinate
     * @param y   = y-coordinate
     * @param map = current map
     */
    public Tile(float x, float y, Map map) {
        super(x, y, map);
        this.livingEntities = new ConcurrentLinkedQueue<>();
    }

    public Tile(float x, float y, Map map, ConcurrentLinkedQueue<LivingEntity> livingEntities) {
        super(x, y, map);
        this.livingEntities = livingEntities;
    }

    public static int getSize() {
        return size;
    }

    public void update() {
    }

    public LivingEntity getClosest(double x, double y) {
        LivingEntity closest = null;
        for (LivingEntity entity : getLivingEntities()) {
            System.out.println(entity);
            if (closest == null) {
                closest = entity;
            } else if (entity == null) {
            } else if (entity.positionDifference(x, y) < closest.positionDifference(x, y)) {
                closest = entity;
            }
        }

        return closest;
    }

    public boolean isLandTile() {
        return true;
    }

    public boolean isAnimated() {
        return false;
    }

    public void addLivingEntity(LivingEntity ent) {
        this.livingEntities.add(ent);
    }

    public void removeLivingEntity(LivingEntity ent) {
        if (this.livingEntities.contains(ent)) {
            this.livingEntities.remove(ent);
        } else {
            System.out.println("remove livingEntity failed");
            System.out.println(ent);
        }
    }

    public void antiSlaughter() {
        for (LivingEntity entity : getLivingEntities()) {
            entity.heal();
        }
    }

    public boolean contains(String type, int amount) {
        for (LivingEntity entity : getLivingEntities()) {
            if (entity.getType().equals(type)) {
                amount--;
            }
        }
        return amount < 1;
    }

    public void slaughter() {
        livingEntities.clear();
    }

    public ConcurrentLinkedQueue<LivingEntity> getLivingEntities() {
        return livingEntities;
    }

    public Color[][] getColors() {
        return colors;
    }

    public String toString() {
        return "Tile(x: " + this.getX() + ", y: " + this.getY() + ") has " + this.livingEntities.size() + "animals" + "%n" + this.livingEntities.toString();
    }

    public void animate() {
    }
}
