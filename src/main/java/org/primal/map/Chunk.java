package org.primal.map;

import org.primal.SimObject;
import org.primal.entity.*;
import org.primal.tile.LandTile;
import org.primal.tile.Tile;

import java.util.concurrent.ThreadLocalRandom;

public class Chunk extends SimObject {
    private Tile[][] tiles;
    private int size = 16;
    private int id;

    public Chunk(float x, float y, Map map) {
        super(x, y, map);
        tiles = new Tile[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                float xPos = i + getX() * this.size;
                float yPos = j + getY() * this.size;
                Tile tile = new LandTile(xPos, yPos, map);
                LivingEntity animal = null;

                int n = ThreadLocalRandom.current().nextInt(0, 3);
                if (n == 0) {
                    animal = new Lion(xPos, yPos, map, 100.0f, 100.0f);
                } else if (n == 1) {
                    animal = new Hyena(xPos, yPos, map, 100.0f, 100.0f);
                } else if (n == 2) {
                    animal = new Giraffe(xPos, yPos, map, 100.0f, 100.0f);
                }

                if (animal != null) tile.addLivingEntity(animal);
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
                    entity.simulate();
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
