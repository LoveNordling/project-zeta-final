package org.primal.map;

import org.primal.SimObject;
import org.primal.entity.*;
import org.primal.tile.LandTile;
import org.primal.tile.Tile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ThreadLocalRandom;

public class Chunk extends SimObject implements Runnable {
    private Tile[][] tiles;
    private int size = 16;
    private int id;
    private Map map;
    private Graphics graphics;

    public Chunk(float x, float y, int id, Map map, Graphics g) {
        super(x, y);
        this.map = map;
        this.id = id;
        this.graphics = g;
        tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {


                LivingEntity entity = null;

                int n = ThreadLocalRandom.current().nextInt(0, 3);
                if (n == 0) {
                    entity = new Lion(i, j, 100.0f, 100.0f, map, g);
                } else if (n == 1) {
                    entity = new Hyena(i, j, 100.0f, 100.0f, map, g);
                } else if (n == 2) {
                    entity = new Giraffe(i, j, 100.0f, 100.0f, map, g);
                }

                Tile tile = new LandTile((float) i, (float) j);

                tile.addLivingEntity(entity);
                tiles[i][j] = tile;
            }
        }
    }

    @Override
    public void run(){
        /*
        try{
            Thread.sleep(3000);
        }
        catch(InterruptedException e){
            System.out.println("Sleep failed");
        }*/

        System.out.println("Uh oh");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (LivingEntity entity : getTile(i, j).getLivingEntities()) {
                    if (entity instanceof Animal) {
                        ((Animal) entity).performAction(map);
                    }
                }
            }
        }
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }
}
