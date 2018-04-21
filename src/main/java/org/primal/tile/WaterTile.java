package org.primal.tile;

import org.primal.entity.LivingEntity;

import java.util.List;

public class WaterTile extends Tile {

    public WaterTile(float x, float y) {
        super(x, y);
    }

    public WaterTile(float x, float y, List<LivingEntity> livingEntities) {
        super(x, y, livingEntities);
    }
}
