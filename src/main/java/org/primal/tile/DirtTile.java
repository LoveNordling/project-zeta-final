package org.primal.tile;

import org.primal.entity.LivingEntity;
import org.primal.map.Map;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class DirtTile extends LandTile {

    public DirtTile(float x, float y, Map map) {
        super(x, y, map);
        randomizePixels(x, y);
    }

    public DirtTile(float x, float y, Map map, ConcurrentLinkedQueue<LivingEntity> livingEntities) {
        super(x, y, map, livingEntities);
        randomizePixels(x, y);
    }

    public void randomizePixels(float x, float y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                colors[i][j] = new Color(219, ThreadLocalRandom.current().nextInt(160, 175), 0);
            }
        }
    }
}
