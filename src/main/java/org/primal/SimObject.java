package org.primal;


import java.awt.*;

public abstract class SimObject {
    protected float[] position;
    protected Shape shape;

    public SimObject(float x, float y) {
        this.position = new float[2];
        position[0] = x;
        position[1] = y;
    }

    public Shape getShape() {
        return shape;
    }


    public float[] getPosition() {
        return position;
    }

    public float getX() {
    	return position[0];
    }

    public float getY() {
    	return position[1];
    }

    public String toString() {
    	return "SimObject(x: " + this.getX() + ",y: " + this.getY() + ")";
    }
}
