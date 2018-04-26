package org.primal.tile;

import org.primal.SimObject;
import org.primal.entity.LivingEntity;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;


public class Tile extends SimObject {
    protected static int size = 30;
    private List<LivingEntity> livingEntities;

    public Tile(float x, float y) {
        super(x, y);
        this.livingEntities = new LinkedList<LivingEntity>();
        this.shape = new Rectangle((int) x * size, (int) y * size, size, size);
    }

    public Tile(float x, float y, List<LivingEntity> livingEntities) {
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

    public List<LivingEntity> getLivingEntities() {
        return livingEntities;
    }

    public String toString(){
        return "Tile(x: " +this.getX() + ", y: "+this.getY() + ") has " + this.livingEntities.size() + "animals" + "%n" + this.livingEntities.toString();
    }
}
