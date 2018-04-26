package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.shape.Shape;

public abstract class Animal extends LivingEntity {
    int starvationRate = 1;
    private int iterTest = 0;
    private int mapSize = 4*16;
    float stamina;
    float fullness;
    private Graphics g;

    LinkedList<Behaviour> behaviours;

    public Animal(float x, float y, float health, float stamina, float fullness){
        // TODO: remove static x y below.
        super(x, y, health);

        this.shape = new Rectangle.Float(this.getPosition()[0] * Tile.getSize(), this.getPosition()[1] * Tile.getSize(), Tile.getSize() / 4, Tile.getSize() / 4);

        this.stamina = stamina;
        this.fullness = fullness;
        //this.shape.setOnMousePressed(click -> System.out.printf("Type: Animal %n Fullness: " + getFullness() + "%n Stamina: " + getStamina() + "%n"));
    }

    public Animal(float x, float y) {
        this(x, y, 100, 100, 100);
    }

    private void moveTile(Tile oldTile, Tile newTile){
        oldTile.removeLivingEntity(this);
        newTile.addLivingEntity(this);
     }
    public void performAction(Map map) {
        mapSize = map.getSize(); //temp solution
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

        if (n == 0 && position[0] < (mapSize -1)) {
            position[0] += 0.1;
        } else if (n == 1 && position[0] > 0) {
            position[0] -= 0.1;
        } else if (n == 2 && position[1] < (mapSize -1)) {
            position[1] += 0.1;
        } else if( n == 3 && position[1] > 0){
            position[1] -= 0.1;
        }
        updateShape();

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
        if (n == 0 && position[0] < (mapSize -1)) {
            position[0] += 1;
        } else if (n == 1 && position[0] > 0) {
            position[0] -= 1;
        } else if (n == 2 && position[1] < (mapSize -1)) {
            position[1] += 1;
        } else if( n == 3 && position[1] > 0){
            position[1] -= 1;
        }
        //System.out.println("set position" + position[0] + "  " + position[1] + iterTest);
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
