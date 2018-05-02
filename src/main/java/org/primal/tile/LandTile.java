package org.primal.tile;

import org.primal.entity.LivingEntity;
import org.primal.map.Map;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LandTile extends Tile {

    public LandTile(float x, float y, Map map) {
        super(x, y, map);
    }

    public LandTile(float x, float y, Map map, ConcurrentLinkedQueue<LivingEntity> livingEntities) {
        super(x, y, map, livingEntities);
    }
}
