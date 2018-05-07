package org.primal;

import org.primal.map.Map;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class SimObject {

    protected Point2D.Float position;
    protected Shape shape;
    protected Map map;

    public SimObject(float x, float y, Map map) {
        this.position = new Point2D.Float(x, y);
        this.map = map;
    }

    public Shape getShape() {
        return shape;
    }

    public Point2D getPosition() {
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
