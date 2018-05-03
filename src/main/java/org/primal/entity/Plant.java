package org.primal.entity;

import org.primal.map.Map;

public abstract class Plant extends LivingEntity {

    public Plant(float x, float y, Map map) {
        super(x, y, map, 100);
    }

    @Override
    public boolean isPlant() {
    	return true;
    }
}
