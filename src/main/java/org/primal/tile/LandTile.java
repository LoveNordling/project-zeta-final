package org.primal.tile;

import org.primal.entity.LivingEntity;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class LandTile extends Tile {

    public LandTile(float x, float y) {
        super(x, y);
    }

    public LandTile(float x, float y, ConcurrentHashMap<Integer,LivingEntity> livingEntities) {
        super(x, y, livingEntities);
    }
}
