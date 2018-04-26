package org.primal.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.primal.behaviour.Behaviour;
import org.primal.map.Map;
import org.primal.tile.Tile;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends LivingEntity {
    int starvationRate = 1;
    private int iterTest = 0;
    float stamina;
    float fullness;
    LinkedList<Behaviour> behaviours;

    public Animal(float x, float y, float health, float stamina, float fullness, Shape shape) {
        // TODO: remove static x y below.
        super(x, y, shape, health);
        this.stamina = stamina;
        this.fullness = fullness;
        this.shape.setOnMousePressed(click -> System.out.printf("Type: Animal %n Fullness: " + getFullness() + "%n Stamina: " + getStamina() + "%n"));
    }

    public Animal(float x, float y) {
        this(x, y, 100, 100, 100, new Circle(x, y, 2, Color.GREEN));
    }

    private void moveTile(Tile oldTile, Tile newTile){
        oldTile.removeLivingEntity(this);
        newTile.addLivingEntity(this);
     }
    public void performAction(Map map) {
        Behaviour best = behaviours.getFirst();
        for (Behaviour behaviour : behaviours) {
            behaviour.decide();
            best = best.getWeight() < behaviour.getWeight() ? behaviour : best;
        }

        float [] currentPos = this.getPosition();
        Tile currentTile = map.getTile(currentPos[0], currentPos[1]);
        best.act();
        float [] newPos = this.getPosition();
        Tile newTile =  map.getTile(newPos[0], newPos[1]);
        if(currentTile != newTile){
            moveTile(currentTile, newTile);
        }
        this.updateShape();
    }

    // Temporary function for random movement
    public void move() {
        int n = ThreadLocalRandom.current().nextInt(0, 4);

        if (n == 0 && position[0] < 14) {
            position[0] += 0.1;
        } else if (n == 1 && position[0] > 1) {
            position[0] -= 0.1;
        } else if (n == 2 && position[0] < 14) {
            position[1] += 0.1;
        } else if( n == 3 && position[0] > 1){
            position[1] -= 0.1;
        }

    }
    //temp func for testing if animal is at edge of map
    public boolean atEdge(Map map){
        float [] pos = this.getPosition();
        ArrayList <Tile> tiles = map.getTiles(pos[0], pos[1], 1);
        if(tiles.size() != 9){
            return true;
        }
        return false;
    }
    //Temp func for testing
    public void move1Unit() {
        int n = ThreadLocalRandom.current().nextInt(0, 4);
        if (n == 0 && position[0] < 15) {
            position[0] += 1;
        } else if (n == 1 && position[0] > 0) {
            position[0] -= 1;
        } else if (n == 2 && position[1] < 15) {
            position[1] += 1;
        } else if( n == 3 && position[1] > 0){
            position[1] -= 1;
        }
        System.out.println("set position" + position[0] + "  " + position[1] + iterTest);
        iterTest++;
        

    }

    public abstract void eat(LivingEntity food);

    public float getFullness() {
        return this.fullness;
    }

    public float getStamina() {
        return this.stamina;
    }
}
