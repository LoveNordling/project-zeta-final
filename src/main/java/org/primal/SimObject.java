package org.primal;

import org.primal.map.Map;
import org.primal.util.Vec2D;

import java.awt.*;


public abstract class SimObject {
    protected Vec2D position;
    protected Shape shape;
    protected Map map;

    /**
     * Creates a SimObject. All objects that are to be simulated are SimObjects.
     * @param x = the X-coordinate
     * @param y = the Y-coordinate
     * @param map = the current Map
     */
    public SimObject(float x, float y, Map map) {
        this.position = new Vec2D(x, y);
        this.map = map;
    }

    /**
     * Returns the shape of the SimObject (the representation of the object)
     * @return Shape the shape of the SimObject
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Returns the position of the SimObject
     * @return Vec2D A vector containing the position on the x- and y-axis.
     */
    public Vec2D getPosition() {
        return position;
    }

    /**
     * Returns the x-coordinate
     * @return float The x-coordinate
     */
    public float getX() {
        return (float) position.getX();
    }

    /**
     * Returns the y-coordinate
     * @return float The y-coordinate
     */
    public float getY() {
        return (float) position.getY();
    }

    public String toString() {
        return "SimObject(x: " + this.getX() + ",y: " + this.getY() + ")";
    }
}
