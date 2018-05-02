package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends LivingEntity {
    private int id;
    float starvationRate = 0.001f;
    private int mapSize = 4 * 16;
    float stamina;
    float fullness;
    private Graphics g;
    private Character[] lastDirections = new Character[4];
    private float lengthUnit = 0.1f;

    LinkedList<Behaviour> behaviours;

    public Animal(float x, float y, Map map, float health, float stamina, float fullness) {
        // TODO: remove static x y below.
        super(x, y, map, health);

        this.shape = new Rectangle.Float(this.getX() * Tile.getSize(), this.getY() * Tile.getSize(), Tile.getSize() / 4, Tile.getSize() / 4);

        this.stamina = stamina;
        this.fullness = fullness;
        energySatisfaction = 100;
        //this.shape.setOnMousePressed(click -> System.out.printf("Type: Animal %n Fullness: " + getFullness() + "%n Stamina: " + getStamina() + "%n"));
    }

    public Animal(float x, float y, Map map) {
        this(x, y, map, 100, 100, 100);
    }

    public void simulate() {
        super.simulate();

        mapSize = map.getSize(); //temp solution
        //Point2D currentPos = this.getPosition();
        Tile currentTile = map.getTile(getX(), getY());

        getBestBehaviour().act();
        updateStats();

        //Point2D newPos = this.getPosition();
        Tile newTile = map.getTile(getX(), getY());
        if (currentTile != newTile) {
            moveTile(currentTile, newTile);
        }
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

    public void move() {
        if (lastDirections[0] != lastDirections[1]) {
            stepInDir(lastDirections[0]);
        } else {
            randomDir();
        }
        updateShape();
    }

    // N = North, S = South, W = West, E = East
    // A = NorthEast, B = SouthEast, C = SouthWest, D = NorthWest
    private void stepInDir(Character c) {
        //float[] newPos = new float[2];
        float newPosX = getX();
        float newPosY = getY();

        switch (c) {
            case 'E':
                newPosX += lengthUnit;
                updateLastDir('E');
                break;
            case 'W':
                newPosX -= lengthUnit;
                updateLastDir('W');
                break;
            case 'N':
                newPosY += lengthUnit;
                updateLastDir('N');
                break;
            case 'S':
                newPosY -= lengthUnit;
                updateLastDir('S');
                break;
            case 'A':
                newPosX += lengthUnit / 2;
                newPosY += lengthUnit / 2;
                updateLastDir('A');
                break;
            case 'B':
                newPosX += lengthUnit / 2;
                newPosY -= lengthUnit / 2;
                updateLastDir('B');
                break;
            case 'C':
                newPosX -= lengthUnit / 2;
                newPosY -= lengthUnit / 2;
                updateLastDir('C');
                break;
            default:
                newPosX -= lengthUnit / 2;
                newPosY += lengthUnit / 2;
                updateLastDir('D');
                break;
        }
        if (map.withinBounds(newPosX, newPosY)) {
            this.position.setLocation(newPosX, newPosY);
        } else {
            randomDir();
        }
    }

    private void randomDir() {
        int n = ThreadLocalRandom.current().nextInt(0, 8);
        switch (n) {
            case 0:
                stepInDir('E');
                break;
            case 1:
                stepInDir('W');
                break;
            case 2:
                stepInDir('N');
                break;
            case 3:
                stepInDir('S');
                break;
            case 4:
                stepInDir('A');
                break;
            case 5:
                stepInDir('B');
                break;
            case 6:
                stepInDir('C');
                break;
            default:
                stepInDir('D');
                break;
        }
    }

    private void updateLastDir(Character c) {
        for (int i = 0; i < lastDirections.length - 1; i++) {
            lastDirections[i + 1] = lastDirections[i];
        }
        lastDirections[0] = c;
    }

    private void moveTile(Tile oldTile, Tile newTile) {
        oldTile.removeLivingEntity(this);
        newTile.addLivingEntity(this);
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

    public float getFullness() {
        return this.fullness;
    }

    public float getStamina() {
        return this.stamina;
    }
}
