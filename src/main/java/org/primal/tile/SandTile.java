package org.primal.tile;

import org.primal.entity.LivingEntity;
import org.primal.map.Map;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class SandTile extends Tile {

    public SandTile(float x, float y, Map map) {
        super(x, y, map);
        randomizePixels(x, y);
    }

    public SandTile(float x, float y, Map map, ConcurrentLinkedQueue<LivingEntity> livingEntities) {
        super(x, y, map, livingEntities);
        randomizePixels(x, y);
    }

    public void randomizePixels(float x, float y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                colors[i][j] = new Color(237, ThreadLocalRandom.current().nextInt(220, 230), 7);
            }
        }
    }
}
