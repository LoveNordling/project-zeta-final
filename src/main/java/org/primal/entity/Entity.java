package org.primal.entity;

import org.primal.SimObject;
import org.primal.map.Map;

public abstract class Entity extends SimObject {

    /**
     * Creates an entity.
     *
     * @param x   = the x-coordinate
     * @param y   = the y-coordinate
     * @param map = the current Map
     */

    public Entity(float x, float y, Map map) {
        super(x, y, map);
    }

    /**
     * The simulation function, used when simulating this object.
     */
    public void simulate() {
    }

}
