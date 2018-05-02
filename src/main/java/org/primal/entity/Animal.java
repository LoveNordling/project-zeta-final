package org.primal.entity;

import org.primal.behaviour.Behaviour;
import org.primal.map.Chunk;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

import java.lang.Math.*;

public abstract class Animal extends LivingEntity {
    private Map map;
    float starvationRate = 0.001f;
    private int mapSize = 4 * 16;
    float stamina;
    float fullness;
    private Graphics g;
    //private Character[] lastDirections = new Character[4];
    private float moveSpeed = 0.05f;
    private double moveDir;
    protected float[] meanGoal =  new float[2];

    LinkedList<Behaviour> behaviours;

    public Animal(float x, float y, float health, float stamina, float fullness, int id) {
        // TODO: remove static x y below.
        super(x, y, health, id);

        this.shape = new Rectangle.Float(this.getPosition()[0] * Tile.getSize(), this.getPosition()[1] * Tile.getSize(), Tile.getSize() / 4, Tile.getSize() / 4);
        this.stamina = stamina;
        this.fullness = fullness;
        energySatisfaction = 100;
        this.moveDir = Math.toRadians(ThreadLocalRandom.current().nextDouble(0, 360));
        //this.shape.setOnMousePressed(click -> System.out.printf("Type: Animal %n Fullness: " + getFullness() + "%n Stamina: " + getStamina() + "%n"));
    }

    public Animal(float x, float y, int id) {
        this(x, y, 100, 100, 100, id);
    }



    public void simulate(Map map) {
        super.simulate(map);

        mapSize = map.getSize(); //temp solution
        float[] currentPos = this.getPosition();
        Tile currentTile = map.getTile(currentPos[0], currentPos[1]);

        getBestBehaviour().act();
        updateStats();

        float[] newPos = this.getPosition();
        /*
        Tile newTile = map.getTile(newPos[0], newPos[1]);
        if (currentTile != newTile) {
            moveTile(currentTile, newTile);
        }*/
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

    public ConcurrentLinkedQueue<Animal> getNeightbours(){

        ConcurrentLinkedQueue<Animal> neightbours = new ConcurrentLinkedQueue();

        int x = Math.round(position[0]);
        int y = Math.round(position[1]);
        for(int i = Math.max(x-2, 0); i < Math.min(x + 2, map.getSize()*Chunk.getSize()); i ++) {
            for(int j = Math.max(y-2, 0); j < Math.min(y + 2, map.getSize()*Chunk.getSize()); j ++) {


                Tile tile = map.getTile(i, j);
                System.out.println("i , j = " + i + " : " + j);
                /*
                ConcurrentLinkedQueue<LivingEntity> entities =  tile.getLivingEntities();

                System.out.println(entities);
                for(LivingEntity e : entities){
                    if(e.getClass() == this.getClass()){
                        neightbours.add((Animal) e);
                    }
                }
*/

            }

        }
        return neightbours;

    }

    public void move(Map map) {
        this.map = map;

        ConcurrentLinkedQueue<Animal> neightbours = getNeightbours();

         meanGoal[0] = 0;
         meanGoal[1] = 0;

        for(Animal neighbour : neightbours){
            if(distance(neighbour) > 1){
                meanGoal[0] += getDistancevector(neighbour)[0];
                meanGoal[1] += getDistancevector(neighbour)[1];
            }
            else {
                meanGoal[0] -= getDistancevector(neighbour)[0];
                meanGoal[1] -= getDistancevector(neighbour)[1];
            }
        }

        double goalAngle = Math.atan(meanGoal[1]/meanGoal[0]);
        if(Math.signum(meanGoal[0]) == 1){
            if(Math.signum(meanGoal[1]) == 1){

            }
                else{
                goalAngle = 360 - goalAngle;
            }
        }
        else{
            if(Math.signum(meanGoal[1]) == 1){
                goalAngle = 180 - goalAngle;
            }
            else{
                goalAngle = 180 + goalAngle;
            }

        }

        double dirChange = Math.toRadians(ThreadLocalRandom.current().nextDouble(-30, 30));


        if(!Double.isNaN(goalAngle)) {
            dirChange = (dirChange +  goalAngle)/2;
        }


        this.moveDir += 0.5*(dirChange - moveDir);

        stepInDir();
        updateShape();
    }

    double distance(Animal a){
        return Math.sqrt(Math.pow(a.getPosition()[0] - this.getPosition()[0], 2) + Math.pow(a.getPosition()[0] - this.getPosition()[0], 2));
    }
    float[] getDistancevector(Animal a){
        float[] ans = new float[2];
        ans[0] = a.getPosition()[0] - this.getPosition()[0];
        ans[1] = a.getPosition()[1] - this.getPosition()[1];
        return ans;
    }

    // N = North, S = South, W = West, E = East
    // A = NorthEast, B = SouthEast, C = SouthWest, D = NorthWest
    private void stepInDir() {
        float velx;
        float vely;

        velx = (float) Math.cos(moveDir)*moveSpeed;
        vely = (float) Math.sin(moveDir)*moveSpeed;

        if (map.withinBounds(position[0] + velx, position[1] + vely)) {
            this.position[0] += velx;
            this.position[1] += vely;
        } else {
            //System.out.println("Hit the corner");
            moveDir += 360/2%360;
        }

    }
    private void moveTile(Tile oldTile, Tile newTile) {
        oldTile.removeLivingEntity(this);
        newTile.addLivingEntity(this.getId(), this);
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
        if (n == 0 && position[0] < (mapSize - 2)) {
            position[0] += 0.1;
        } else if (n == 1 && position[0] > 1) {
            position[0] -= 0.1;
        } else if (n == 2 && position[1] < (mapSize - 2)) {
            position[1] += 0.1;
        } else if (n == 3 && position[1] > 1) {
            position[1] -= 0.1;
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
