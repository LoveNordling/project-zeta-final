package org.primal.tile;

import org.primal.entity.LivingEntity;
import org.primal.map.Map;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class WaterTile extends Tile {

    private int updateCount = 0;

    public WaterTile(float x, float y, Map map) {
        super(x, y, map);
        randomizePixels(x, y);
    }

    public WaterTile(float x, float y, Map map, ConcurrentLinkedQueue<LivingEntity> livingEntities) {
        super(x, y, map, livingEntities);
        randomizePixels(x, y);
    }

    public void randomizePixels(float x, float y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                colors[i][j] = new Color(2, ThreadLocalRandom.current().nextInt(157, 177), 219);
            }
        }
    }

    public void animate() {
        randomizePixels(getX(), getY());
    }

    public boolean isLandTile() {
        return false;
    }

    public boolean isAnimated() {
        return true;
    }
}
