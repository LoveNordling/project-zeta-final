package org.primal.tile;

import org.primal.SimObject;
import org.primal.entity.LivingEntity;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Tile extends SimObject {
    protected static int size = 30;
    private ConcurrentLinkedQueue<LivingEntity> livingEntities;

    public Tile(float x, float y) {
        super(x, y);
        this.livingEntities = new ConcurrentLinkedQueue<LivingEntity>();
        this.shape = new Rectangle((int) x * size, (int) y * size, size, size);
    }

    public Tile(float x, float y, ConcurrentLinkedQueue<LivingEntity> livingEntities) {
        super(x, y);
        this.livingEntities = livingEntities;
    }

    public static int getSize() {
        return size;
    }

    public void addLivingEntity(LivingEntity ent) {
        this.livingEntities.add(ent);
    }

    public void removeLivingEntity(LivingEntity ent) {
        if (this.livingEntities.contains(ent)) {
            this.livingEntities.remove(ent);
        }
    }

    public ConcurrentLinkedQueue<LivingEntity> getLivingEntities() {
        return livingEntities;
    }

    public String toString() {
        return "Tile(x: " + this.getX() + ", y: " + this.getY() + ") has " + this.livingEntities.size() + "animals" + "%n" + this.livingEntities.toString();
    }
}
