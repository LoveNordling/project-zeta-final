package org.primal.entity;

import org.primal.SimObject;


public abstract class Entity extends SimObject {
    public Entity(float x, float y) {
        super(x, y);
    }

    public void simulate() {};
}
