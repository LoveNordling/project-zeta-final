package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.map.Map;
import org.primal.tile.Tile;
import org.primal.util.Vec2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public abstract class Animal extends LivingEntity {

    private static AtomicInteger counter = new AtomicInteger();
    protected float speed = 0.05f;
    protected Vec2D movementDirection;
    float starvationRate = 0.0001f;
    float stamina;
    float fullness;
    LinkedList<Behaviour> behaviours;
    private int id;
    private int mapSize = 4 * 16;
    private Graphics g;
    private Character[] lastDirections = new Character[4];

    /**
     * Creates an Animal object.
     *
     * @param x        = the x-coordinate
     * @param y        = the y-coordinate
     * @param map      = the current Map
     * @param health   = starting health-points
     * @param stamina  = starting stamina-points
     * @param fullness = starting fullness points
     */
    public Animal(float x, float y, Map map, float health, float stamina, float fullness) {
        // TODO: remove static x y below.
        super(x, y, map, health);

        this.shape = new Ellipse2D.Float(this.getX() * Tile.getSize(), this.getY() * Tile.getSize(), Tile.getSize() / 8, Tile.getSize() / 8);

        this.stamina = stamina;
        this.fullness = fullness;
        energySatisfaction = 100;
        this.id = counter.getAndIncrement();
        double startAngle = Math.toRadians(ThreadLocalRandom.current().nextDouble(0, 360));

        this.movementDirection = new Vec2D((float) Math.cos(startAngle), (float) Math.sin(startAngle));


    }

    /**
     * Creates an animal object. Health, stamina, and fullness will be set to 0.
     *
     * @param x   = the x-coordinate
     * @param y   = the y-coordinate
     * @param map = the current Map
     */
    public Animal(float x, float y, Map map) {
        this(x, y, map, 100, 100, 100);
    }

    /**
     * The simulation method used when simulating an animal. Goes through the list of behaviours and weighs
     * which option is the most important. The behaviour is the acted upon and the stats of the animal is updated.
     */
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

    /**
     * Goes through the list of behaviours and returns the most heavily weighted option.
     *
     * @return Behaviour The option currently being prioritized.
     */
    private Behaviour getBestBehaviour() {
        Behaviour best = behaviours.getFirst();
        for (Behaviour behaviour : behaviours) {
            behaviour.decide();
            best = best.getWeight() < behaviour.getWeight() ? behaviour : best;
        }
        return best;
    }

    /**
     * Updates the animal's stamina, fullness, health and energy.
     */
    private void updateStats() {
        if (stamina > 0 && fullness > 0) {
            stamina -= starvationRate;
            fullness -= starvationRate;
        } else if (fullness <= 0) {
            health -= starvationRate;
            if (health <= 0) {
                starve();
            }
        } else {
            fullness -= starvationRate;
        }
    }

    /**
     * Checks whether the object is an animal
     *
     * @return true
     */
    @Override
    public boolean isAnimal() {
        return true;
    }

    /**
     * Moves the Animal to a new point in the map. Checks for collision so it does not move out of bounds.
     */
    public void move() {
        Vec2D newPos = new Vec2D((this.position.getX() + movementDirection.getX() * speed), (this.position.getY() + movementDirection.getY() * speed));
        Vec2D collisionPoint = map.checkCollision(newPos.getX(), newPos.getY());
        map.checkTileCollision(newPos.getX(), newPos.getY());
        if (collisionPoint.getX() == 0 && collisionPoint.getY() == 0) {
            this.position.setLocation(Math.max(newPos.getX(), 0), Math.max(newPos.getY(), 0));
        } else {

            float dotProduct = (float) (movementDirection.getX() * collisionPoint.getX() + movementDirection.getY() * collisionPoint.getY());
            movementDirection.setLocation(movementDirection.getX() - 2 * collisionPoint.getX() * dotProduct, movementDirection.getY() - 2 * collisionPoint.getY() * dotProduct);

            move();
        }

        updateShape();
    }

    /**
     * Moves all objects on a tile to a new tile.
     *
     * @param oldTile = the tile whose entities should be moved from.
     * @param newTile = the target tile for the entities.
     */
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

    /**
     * Returns the unique ID of an animal.
     *
     * @return int the ID of the animal
     */
    public int getId() {
        return id;
    }

    //temp func for testing if animal is at edge of map

    /**
     * Checks whether an animal is at the edge of the map.
     *
     * @return true if on the edge, else false
     */
    public boolean atEdge() {
        //float[] pos = this.getPosition();
        ArrayList<Tile> tiles = map.getTiles(getX(), getY(), 1);
        return tiles.size() != 9;
    }

    //Temp func for testing

    /**
     * Moves a entity in a random direction.
     */
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

    /**
     * Function for eating another living entity.
     *
     * @param food = the entity to be eaten
     */
    public abstract void eat(LivingEntity food);


    /**
     * Updates the position of an animal.
     *
     * @param p = the current position
     */
    public void setPosition(Vec2D p) {
        this.position = p;
    }

    /**
     * Returns the fullness of the animal (hunger)
     *
     * @return float The hunger level of the animal
     */
    public float getFullness() {
        return this.fullness;
    }

    /**
     * Returns the stamina of the animal
     *
     * @return float The stamina level
     */
    public float getStamina() {
        return this.stamina;
    }


    /**
     * Returns the speed of the animal. Decides how far the animal travels per tick.
     *
     * @return float The current speed.
     */
    public float getSpeed() {
        return this.speed;
    }

    /**
     * Returns the direction the animal is currently facing.
     *
     * @return Point2D The direction of the animal.
     */
    public Vec2D getDirection() {
        return this.movementDirection;
    }

    /** breed is used by those who override it to generate an animal of the same race
     */
    public void breed(){}


    /**
     * Sets the direction of the animals movement
     *
     * @param p = the current position
     */
    public void setDirection(Vec2D p) {
        this.movementDirection = p;
    }

    public abstract void starve();
}
