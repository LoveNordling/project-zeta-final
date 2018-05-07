package org.primal.tile;

import org.primal.SimObject;
import org.primal.entity.LivingEntity;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;


public class Tile extends SimObject {
    protected static int size = 30;
    private ConcurrentHashMap<Integer,LivingEntity> livingEntities;

    public Tile(float x, float y) {
        super(x, y);
        this.livingEntities = new ConcurrentHashMap<Integer,LivingEntity>();
        this.shape = new Rectangle((int) x * size, (int) y * size, size, size);
    }

    public Tile(float x, float y, ConcurrentHashMap<Integer,LivingEntity> livingEntities) {
        super(x, y);
        this.livingEntities = livingEntities;
    }

    public static int getSize() {
        return size;
    }

    public void addLivingEntity(int K, LivingEntity ent) {
        this.livingEntities.put(K, ent);
    }

    public void removeLivingEntity(LivingEntity ent) {
        if (this.livingEntities.contains(ent)) {
            this.livingEntities.remove(ent);
        }
        else{
            System.out.println("remove livingEntity failed");
        }
    }
    
    public void antiSlaughter(){
        for (LivingEntity entity : getLivingEntities().values()) {
            entity.heal();
        }
    }
    
    public void slaughter(){
        livingEntities.clear();
    }
    
    public ConcurrentHashMap<Integer,LivingEntity> getLivingEntities() {
        return livingEntities;
    }

    public String toString() {
        return "Tile(x: " + this.getX() + ", y: " + this.getY() + ") has " + this.livingEntities.size() + "animals" + "%n" + this.livingEntities.toString();
    }
}
