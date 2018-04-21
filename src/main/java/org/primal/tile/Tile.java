package org.primal.tile;

import org.primal.SimObject;
import org.primal.entity.LivingEntity;

import java.util.LinkedList;
import java.util.List;


public class Tile extends SimObject {
    private List<LivingEntity> livingEntities;

    public Tile(float x, float y) {
        super(x, y);
        this.livingEntities = new LinkedList<LivingEntity>();
    }

    public Tile(float x, float y, List<LivingEntity> livingEntities) {
        super(x, y);
        this.livingEntities = livingEntities;
    }

    public void addLivingEntity(LivingEntity ent) {
        this.livingEntities.add(ent);
    }

    public void removeLivingEntity(LivingEntity ent) {
        if (this.livingEntities.contains(ent)) {
            this.livingEntities.remove(ent);
        }
    }

    public List<LivingEntity> getLivingEntities() {
        return livingEntities;
    }
}
