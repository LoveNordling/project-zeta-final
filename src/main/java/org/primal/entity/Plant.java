package org.primal.entity;

import org.primal.map.Map;

public abstract class Plant extends LivingEntity {

    /**
     * Creates a plant object
     *
     * @param x   = the x-coordinate
     * @param y   = the y-coordinate
     * @param map = the current Map
     */

    public Plant(float x, float y, Map map) {
        super(x, y, map, 100);
    }

    /**
     * Checks whether entity is a plant
     *
     * @return true
     */
    @Override
    public boolean isPlant() {
        return true;
    }
}
