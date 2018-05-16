package org.primal.entity;

import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public abstract class LivingEntity extends Entity {

    protected Ellipse2D.Float shape;
    protected Color color;
    protected float shapeSize;
    protected String lastAction = "Nothing";
    float health;
    private float maxHP;
    private boolean isAlive;

    /**
     * Creates a living entity. Classes such as Animals extends this class.
     *
     * @param x      = the x-coordinate
     * @param y      = the y-coordinate
     * @param map    = the current Map
     * @param health = starting health points
     */
    public LivingEntity(float x, float y, Map map, float health) {
        super(x, y, map);
        this.color = new Color(0, 0, 0);
        this.health = health;
        this.maxHP = health;
        this.isAlive = true;
    }

    /**
     * Updates the shape of the object. Shapes are assumed to be rectangles.
     */
    public void updateShape() {
        if (this.isAnimal()) {
            float size = this.shapeSize;
            this.shape.setFrame(getX() * Tile.TILE_SIZE - size / 2, getY() * Tile.TILE_SIZE - size / 2, size, size);
        }
    }

    /**
     * Checks whether an object is an animal. Should be overridden.
     *
     * @return false
     */
    public boolean isAnimal() {
        return false;
    }

    /**
     * Checks whether an object is a plant. Should be overridden.
     *
     * @return false
     */
    public boolean isPlant() {
        return false;
    }

    /**
     * Returns the objects shape.
     *
     * @return Shape The objects shape.
     */
    public Shape getShape() {
        updateShape();
        return this.shape;
    }

    /**
     * Simulation function, used when simulating an object.
     */
    public void simulate() {
    }

    /**
     * Sets the health of the object to max capacity.
     */
    public void heal() {
        health = maxHP;
    }

    /**
     * Returns the color of the object's shape.
     *
     * @return Color The color of the object's shape.
     */
    public Color getColor() {
        return color;
    }

    /** getType the type of the living entity used to check if 2 living entities is the same type
     *  
     * @return it's type which is an empty string unless overriden
     */
    public String getType(){
        return "";
    }


    /**
     * Returns whether this {@code LivingEntity} is alive or not.
     *
     * @return {@code True} if this {@code LivingEntity} is alive, otherwise {@code false}.
     */
    public boolean isAlive() {
        return this.isAlive;
    }

    /**
     * Sets this {@code LivingEntity} to dead.
     *
     */
    public void die() {
        this.isAlive = false;
    }

    public double positionDifference(double x, double y){
        double xDiff = x - this.getX();
        double yDiff = y - this.getY();
        xDiff = xDiff*xDiff;
        yDiff = yDiff*yDiff;
        return Math.sqrt(xDiff + yDiff);
    }
    public String getLastAction(){
        return lastAction;
    }
    public void setLastAction(String s){
        lastAction = s;
    }
    public float getHealth(){
        return health;
    }
    public String additionalInfo(){
        return "";
    }
}
