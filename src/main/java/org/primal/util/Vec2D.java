package org.primal.util;

import java.awt.geom.Point2D;

public class Vec2D extends Point2D.Double {

    public Vec2D(double x, double y){
        super(x,y);
    }

    public Vec2D plus(Vec2D v){
        return new Vec2D(this.getX() + v.getX(), this.getY() + v.getY());
    }

    public Vec2D minus(Vec2D v){
        return new Vec2D(this.getX() - v.getX(), this.getY() - v.getY());
    }

    public double dot(Vec2D v){
        return this.getX() * v.getX() + this.getY() * v.getY();
    }

    public double length(){
        return Math.sqrt(Math.pow(this.getX(),2) + Math.pow(this.getY(),2));
    }

    public void normalize(){
        double l = this.length();
        this.setLocation(this.getX()/l, this.getY()/l);
    }
}