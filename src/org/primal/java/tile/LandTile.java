package org.primal.java.tile;

import org.primal.java.entity.LivingEntity;

import java.util.List;

public class LandTile extends Tile {
    public LandTile(float x, float y, List<LivingEntity> livingEntities) {
        super(x, y, livingEntities);
    }
}
