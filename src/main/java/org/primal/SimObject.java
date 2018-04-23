package org.primal;

public abstract class SimObject {
    protected float[] position;

    public SimObject(float x, float y) {
        this.position = new float[2];
        position[0] = x;
        position[1] = y;
    }

    public float[] getPosition() {
        return position;
    }
}
