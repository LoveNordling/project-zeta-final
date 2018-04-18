package org.primal.java.tile;

import org.primal.java.SimObject;
import org.primal.java.entity.LivingEntity;

import java.util.List;


public class Tile extends SimObject {
    private List<LivingEntity> livingEntities;

    public Tile(float x, float y, List<LivingEntity> livingEntities) {
        super(x, y);
        this.livingEntities = livingEntities;
    }

    public List<LivingEntity> getLivingEntities() {
        return livingEntities;
    }
}
