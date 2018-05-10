package org.primal.util;

import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

public class Vec2D extends Point2D.Double {

    public Vec2D(double x, double y){
        super(x,y);
    }


    /**
     * Returns self added with v
     * @return Vec2D the sum
     */
    public Vec2D plus(Vec2D v){
        return new Vec2D(this.getX() + v.getX(), this.getY() + v.getY());
    }

    /**
     * Returns self subtracted by v
     * @return Vec2D self - v
     */
    public Vec2D minus(Vec2D v){
        return new Vec2D(this.getX() - v.getX(), this.getY() - v.getY());
    }

    /**
     * Returns the dot product of self and v
     * @return double the dot product
     */
    public double dot(Vec2D v){
        return this.getX() * v.getX() + this.getY() * v.getY();
    }


    public Vec2D times(double n){return new Vec2D(getX()*n, getY()*n); }


    public Vec2D randomRadius(double r){
        double angle = Math.toRadians(ThreadLocalRandom.current().nextDouble(0, 360));
        double radius = ThreadLocalRandom.current().nextDouble(0, r);
        double x = this.getX() + radius*Math.cos(angle);
        double y = this.getY() + radius*Math.sin(angle);
        return new Vec2D(x, y);
    }


    /**
     * Returns the length of self
     * @return double length
     */
    public double length(){
        return Math.sqrt(Math.pow(this.getX(),2) + Math.pow(this.getY(),2));
    }

    /**
     * Returns a vector with the same direction as self but with length 1
     * @return Vec2D the normalized vector
     */
    public Vec2D normalize(){
        double l = this.length();
        return new Vec2D(this.getX()/l, this.getY()/l);
    }
}