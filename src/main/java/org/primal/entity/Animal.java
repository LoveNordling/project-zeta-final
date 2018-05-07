package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.geom.Point2D;

public abstract class Animal extends LivingEntity {

    float starvationRate = 0.001f;
    float stamina;
    float fullness;
    LinkedList<Behaviour> behaviours;
    private int id;
    private int mapSize = 4 * 16;
    private Graphics g;
    private Character[] lastDirections = new Character[4];
    protected float speed = 0.1f;
    protected Point2D.Float movementDirection;

    public Animal(float x, float y, Map map, float health, float stamina, float fullness) {
        // TODO: remove static x y below.
        super(x, y, map, health);

        this.shape = new Rectangle.Float(this.getX() * Tile.getSize(), this.getY() * Tile.getSize(), Tile.getSize() / 8, Tile.getSize() / 8);

        this.stamina = stamina;
        this.fullness = fullness;
        energySatisfaction = 100;
        double startAngle = Math.toRadians(ThreadLocalRandom.current().nextDouble(0, 360));
        this.movementDirection = new Point2D.Float((float)Math.cos(startAngle), (float)Math.sin(startAngle));

    }

    public Animal(float x, float y, Map map) {
        this(x, y, map, 100, 100, 100);
    }

    public void simulate() {
        super.simulate();

        mapSize = map.getSize(); //temp solution
        Tile currentTile = map.getTile(getX(), getY());
        Behaviour best = getBestBehaviour();
        best.act();


        updateStats();

        Tile newTile = map.getTile(getX(), getY());
        if (currentTile != newTile) {
            moveTile(currentTile, newTile);
        }

    }

    protected Point2D normalize(Point2D p){
        double x = p.getX();
        double y = p.getY();
        float abs = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return new Point2D.Float((float) x/abs, (float) y / abs);
    }

    private Behaviour getBestBehaviour() {
        Behaviour best = behaviours.getFirst();
        for (Behaviour behaviour : behaviours) {
            behaviour.decide();
            best = best.getWeight() < behaviour.getWeight() ? behaviour : best;
        }
        return best;
    }

    private void updateStats() {
        if (stamina > 0 && fullness > 0) {
            stamina -= starvationRate;
            fullness -= starvationRate;
        } else if (energySatisfaction > 0) {
            energySatisfaction -= starvationRate;
        }
        if (energySatisfaction < 50 && health <= 0) {
            health -= starvationRate * 10;
        }
    }


    @Override
    public boolean isAnimal() {
        return true;
    }

    public boolean isValidMovement(Point2D.Float newPos) {
        if(map.getTile((float) newPos.getX(),(float) newPos.getY()).isWaterTile()){
            return false;
        }
        // Add further checks here

        return true;
    }

    public void move() {
        Point2D.Float newPos = new Point2D.Float((float)(this.position.getX() + movementDirection.getX()*speed), (float)(this.position.getY() + movementDirection.getY()*speed));
        Point2D collisionPoint = map.checkCollision((float)newPos.getX(), (float)newPos.getY());
        if (collisionPoint.getX() == 0 && collisionPoint.getY() == 0 && isValidMovement(newPos)) {
            this.position.setLocation(Math.max(newPos.getX(),0), Math.max(newPos.getY(), 0));
        } else {

            float dotProduct = (float)(movementDirection.getX()*collisionPoint.getX() +  movementDirection.getY()*collisionPoint.getY());
            movementDirection.setLocation(movementDirection.getX()-2*collisionPoint.getX()*dotProduct, movementDirection.getY()-2*collisionPoint.getY()*dotProduct);

            move();
        }


        updateShape();
    }

    private void moveTile(Tile oldTile, Tile newTile) {
        
        oldTile.removeLivingEntity(this);
        newTile.addLivingEntity(this);
    }
    
    private void updateLastDir(Character c) {
        for (int i = 0; i < lastDirections.length - 1; i++) {
            lastDirections[i + 1] = lastDirections[i];
        }
        lastDirections[0] = c;
    }

    public int getId() {
        return id;
    }

    //temp func for testing if animal is at edge of map
    public boolean atEdge() {
        //float[] pos = this.getPosition();
        ArrayList<Tile> tiles = map.getTiles(getX(), getY(), 1);
        if (tiles.size() != 9) {
            return true;
        }
        return false;
    }

    //Temp func for testing
    public void move1Unit() {
        int n = ThreadLocalRandom.current().nextInt(0, 4);
        if (n == 0 && getX() < (mapSize - 2)) {
            this.position.setLocation(getX() + 0.1, getY());
        } else if (n == 1 && getX() > 1) {
            this.position.setLocation(getX() - 0.1, getY());
        } else if (n == 2 && getY() < (mapSize - 2)) {
            this.position.setLocation(getX(), getY() + 0.1);
        } else if (n == 3 && getY() > 1) {
            this.position.setLocation(getX(), getY() - 0.1);
        }
    }

    public abstract void eat(LivingEntity food);

    public void setPosition(Point2D.Float p){
        this.position = p;
    }
    public void setDirection(Point2D.Float p){this.movementDirection = p; }

    public float getFullness() {
        return this.fullness;
    }

    public float getStamina() {
        return this.stamina;
    }

    public float getSpeed(){return this.speed; }
    public Point2D.Float getDirection(){return this.movementDirection; }
}
