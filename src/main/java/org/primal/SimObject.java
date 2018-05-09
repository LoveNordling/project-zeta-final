package org.primal;

import org.primal.map.Map;
import org.primal.util.Vec2D;

import java.awt.*;

public abstract class SimObject {

    protected Vec2D position;
    protected Shape shape;
    protected Map map;

    public SimObject(float x, float y, Map map) {
        this.position = new Vec2D(x, y);
        this.map = map;
    }

    public Shape getShape() {
        return shape;
    }

    public Vec2D getPosition() {
        return position;
    }

    public float getX() {
        return (float) position.getX();
    }

    public float getY() {
        return (float) position.getY();
    }

    public String toString() {
        return "SimObject(x: " + this.getX() + ",y: " + this.getY() + ")";
    }
}
