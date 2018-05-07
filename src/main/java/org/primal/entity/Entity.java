package org.primal.entity;

import org.primal.SimObject;
import org.primal.map.Map;

public abstract class Entity extends SimObject {

    public Entity(float x, float y, Map map) {
        super(x, y, map);
    }

    public void simulate() {
    }

    ;
}
