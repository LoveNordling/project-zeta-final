package org.primal.tile;

import org.primal.SimObject;
import org.primal.entity.LivingEntity;
import org.primal.map.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Tile extends SimObject {

    private static int size = 30;
    private ConcurrentLinkedQueue<LivingEntity> livingEntities;
    private List<Pixel> pixels;

    public Tile(float x, float y, Map map) {
        super(x, y, map);
        this.livingEntities = new ConcurrentLinkedQueue<>();
        this.shape = new Rectangle((int) x * size, (int) y * size, size, size);
        this.pixels = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // TODO: Abstract color so it is instead defined in the specific tile, LandTile, WaterTile, etc.
                Color color = new Color(ThreadLocalRandom.current().nextInt(165, 205), 204, 8);
                pixels.add(new Pixel(new Rectangle(((int) x * 30) + (i * 10), ((int) y * 30) + (j * 10), 10, 10), color));
            }
        }
    }

    public Tile(float x, float y, Map map, ConcurrentLinkedQueue<LivingEntity> livingEntities) {
        super(x, y, map);
        this.livingEntities = livingEntities;
    }

    public static int getSize() {
        return size;
    }

    public List<Pixel> getPixels() {
        return this.pixels;
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
