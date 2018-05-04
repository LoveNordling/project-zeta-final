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
    private float lengthUnit = 1.0f;

    LinkedList<Behaviour> behaviours;

    public Animal(float x, float y, Map map, float health, float stamina, float fullness) {
        // TODO: remove static x y below.
        super(x, y, map, health);

        this.shape = new Rectangle.Float(this.getX(), this.getY(), Tile.getSize() / 4, Tile.getSize() / 4);

        this.stamina = stamina;
        this.fullness = fullness;
        energySatisfaction = 100;
        //this.shape.setOnMousePressed(click -> System.out.printf("Type: Animal %n Fullness: " + getFullness() + "%n Stamina: " + getStamina() + "%n"));
    }

    public Animal(float x, float y, Map map) {
        this(x, y, map, 100, 100, 100);
    }

    public void simulate() {

        // System.out.println("super simulate");
        super.simulate();
        // System.out.println("Animal simulate");

        // System.out.println("Getting mapSize");
        mapSize = map.getSize(); //temp solution
        // System.out.println("Got mapSize");
        //Point2D currentPos = this.getPosition();
        // System.out.println("Getting oldtile");
        Tile currentTile = map.getTile(getX() / Tile.getSize(), getY() / Tile.getSize());
        // System.out.println("Got oldTile");

        // System.out.println("Getting behaviour");
        getBestBehaviour().act();
        // System.out.println("Got behaviour");
        // System.out.println("Getting stats");
        updateStats();
        // System.out.println("Got stats");

        //Point2D newPos = this.getPosition();
        // System.out.println("Getting newtile");
        Tile newTile = map.getTile(getX() / Tile.getSize(), getY() / Tile.getSize());
        // System.out.println("Got newtile");
        if (currentTile != newTile) {
            // System.out.println("Getting movetile");
            moveTile(currentTile, newTile);
            // System.out.println("Got movetile");
        }
    }

    private Behaviour getBestBehaviour() {
        // System.out.println("Getting first behaviour");
        Behaviour best = behaviours.getFirst();
    // System.out.println("Got first behaviour");
        for (Behaviour behaviour : behaviours) {
            // System.out.println("Getting decide");
            behaviour.decide();
            // System.out.println("Got decide");
            // System.out.println("Getting best");
            best = best.getWeight() < behaviour.getWeight() ? behaviour : best;
            // System.out.println("Got best");
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

    public void move() {
        // System.out.println("entering move");
        if (lastDirections[0] != lastDirections[1]) {
            // System.out.println("stepInDir");
            stepInDir(lastDirections[0]);
            // System.out.println("after stepInDir");
        } else {
            // System.out.println("randomDir");
            randomDir();
            // System.out.println("after randomDir");
        }
        // System.out.println("updateShape");
        updateShape();
        // System.out.println("after updateShape");
    }

    // N = North, S = South, W = West, E = East
    // A = NorthEast, B = SouthEast, C = SouthWest, D = NorthWest
    private void stepInDir(Character c) {
        //float[] newPos = new float[2];
        // System.out.println("Getting pos");
        float newPosX = getX();
        float newPosY = getY();
        // System.out.println("Got pos x: " + newPosX + ", y: " + newPosY);
        // System.out.println(c);

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
        // System.out.println("did stuff");
        if (map.withinBounds(newPosX, newPosY)) {
            // System.out.println("(Stepindir) Moving to: " + newPosX + "," + newPosY);
            this.position.setLocation(newPosX, newPosY);
        } else {
            randomDir();
        }
    }

    private void randomDir() {
        // System.out.println("Getting ThreadLocalRandom");
        int n = ThreadLocalRandom.current().nextInt(0, 8);
        // System.out.println(n);
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
