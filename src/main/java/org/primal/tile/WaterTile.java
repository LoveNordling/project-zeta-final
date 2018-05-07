package org.primal.tile;

import org.primal.entity.LivingEntity;
import org.primal.map.Map;

import java.util.concurrent.ConcurrentLinkedQueue;

public class WaterTile extends Tile {

    public WaterTile(float x, float y, Map map) {
        super(x, y, map);
    }

    public WaterTile(float x, float y, Map map, ConcurrentLinkedQueue<LivingEntity> livingEntities) {
        super(x, y, map, livingEntities);
    }
}
