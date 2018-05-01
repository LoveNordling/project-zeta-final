package org.primal.map;

import org.primal.SimObject;
import org.primal.entity.*;
import org.primal.tile.LandTile;
import org.primal.tile.Tile;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Chunk extends SimObject {
    private Tile[][] tiles;
    private int size = 16;
    private int id;
    private Map map;

    public Chunk(float x, float y, Map map) {
        super(x, y);
        this.map = map;
        tiles = new Tile[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                float xPos = i + getX() * this.size;
                float yPos = j + getY() * this.size;
                LivingEntity entity = null;

                int n = ThreadLocalRandom.current().nextInt(0, 3);
                if (n == 0) {
                    entity = new Lion(xPos, yPos, 100.0f, 100.0f, map);
                } else if (n == 1) {
                    entity = new Hyena(xPos, yPos, 100.0f, 100.0f, map);
                } else if (n == 2) {
                    entity = new Giraffe(xPos, yPos, 100.0f, 100.0f, map);
                }
                Tile tile = new LandTile(xPos, yPos);
                tile.addLivingEntity(entity);
                tiles[i][j] = tile;
            }
        }
    }

    public void updateChunk() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Sleep failed");
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (LivingEntity entity : getTile(i, j).getLivingEntities()) {
                    entity.simulate(this.map);
                }
            }
        }
        //System.out.println("moved to"+ getTile(0,0).getLivingEntities().get(0).getPosition()[0]);
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
