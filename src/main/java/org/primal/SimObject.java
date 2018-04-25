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
}
