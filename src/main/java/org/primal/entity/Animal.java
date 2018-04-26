package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends LivingEntity {
    float starvationRate = 0.001f;
    private int mapSize = 4 * 16;
    float stamina;
    float fullness;
    private Graphics g;
    private Character[] lastDirections = new Character[4];
    private float lengthUnit = 0.1f;

    LinkedList<Behaviour> behaviours;

    public Animal(float x, float y, float health, float stamina, float fullness) {
        // TODO: remove static x y below.
        super(x, y, health);

        this.shape = new Rectangle.Float(this.getPosition()[0] * Tile.getSize(), this.getPosition()[1] * Tile.getSize(), Tile.getSize() / 4, Tile.getSize() / 4);

        this.stamina = stamina;
        this.fullness = fullness;
        energySatisfaction = 100;
        //this.shape.setOnMousePressed(click -> System.out.printf("Type: Animal %n Fullness: " + getFullness() + "%n Stamina: " + getStamina() + "%n"));
    }

    public Animal(float x, float y) {
        this(x, y, 100, 100, 100);
    }

    public void simulate(Map map) {
        super.simulate(map);

        mapSize = map.getSize(); //temp solution
        float[] currentPos = this.getPosition();
        Tile currentTile = map.getTile(currentPos[0], currentPos[1]);

        getBestBehaviour().act();
        updateStats();

        float[] newPos = this.getPosition();
        Tile newTile = map.getTile(newPos[0], newPos[1]);
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
    // A = NorthEast, B = SouthEast, C = SouthWest, D = SouthEast
    private void stepInDir(Character c) {
        switch (c) {
            case 'E':
                position[0] += lengthUnit;
                updateLastDir('E');
                break;
            case 'W':
                position[0] -= lengthUnit;
                updateLastDir('W');
                break;
            case 'N':
                position[1] += lengthUnit;
                updateLastDir('N');
                break;
            case 'S':
                position[1] -= lengthUnit;
                updateLastDir('S');
                break;
            case 'A':
                position[0] += lengthUnit / 2;
                position[1] += lengthUnit / 2;
                updateLastDir('A');
                break;
            case 'B':
                position[0] += lengthUnit / 2;
                position[1] -= lengthUnit / 2;
                updateLastDir('B');
                break;
            case 'C':
                position[0] -= lengthUnit / 2;
                position[1] -= lengthUnit / 2;
                updateLastDir('C');
                break;
            default:
                position[0] -= lengthUnit / 2;
                position[1] += lengthUnit / 2;
                updateLastDir('D');
                break;
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

    //temp func for testing if animal is at edge of map
    public boolean atEdge(Map map) {
        float[] pos = this.getPosition();
        ArrayList<Tile> tiles = map.getTiles(pos[0], pos[1], 1);
        if (tiles.size() != 9) {
            return true;
        }
        return false;
    }

    //Temp func for testing
    public void move1Unit() {
        int n = ThreadLocalRandom.current().nextInt(0, 4);
        if (n == 0 && position[0] < (mapSize - 1)) {
            position[0] += 1;
        } else if (n == 1 && position[0] > 0) {
            position[0] -= 1;
        } else if (n == 2 && position[1] < (mapSize - 1)) {
            position[1] += 1;
        } else if (n == 3 && position[1] > 0) {
            position[1] -= 1;
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
