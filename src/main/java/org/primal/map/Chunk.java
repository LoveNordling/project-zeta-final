package org.primal.map;

import org.primal.SimObject;
import org.primal.entity.*;
import org.primal.tile.LandTile;
import org.primal.tile.Tile;

import java.util.concurrent.ThreadLocalRandom;

public class Chunk extends SimObject {
    private Tile[][] tiles;
    private static int size = 4;
    private int id;
    private Map map;

    public Chunk(float x, float y, Map map) {
        super(x, y);
        this.map = map;
        tiles = new Tile[size][size];


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                float xPos = i + getPosition()[0] * this.size;
                float yPos = j + getPosition()[1] * this.size;

                Tile tile = new LandTile(xPos, yPos);
                tiles[i][j] = tile;
            }
        }
        float xPos = (getPosition()[0] + 0.5f)* this.size;
        float yPos = (getPosition()[1] + 0.5f) * this.size;

        LivingEntity entity = null;
        int n = ThreadLocalRandom.current().nextInt(0, 3);
        Tile spawnTile = getTile(size/2, size/2);
        for(int i = 0; i < 1; i++) {
            int id = map.entityId.incrementAndGet();
            if (n == 0) {
                entity = new Lion(xPos, yPos, 100.0f, 100.0f, map, id);
            } else if (n == 1) {
                entity = new Hyena(xPos, yPos, 100.0f, 100.0f, map, id);
            } else if (n == 2) {
                entity = new Giraffe(xPos, yPos, 100.0f, 100.0f, map, id);
            }

            spawnTile.addLivingEntity(entity.getId(), entity);
        }



    }

    public void updateChunk() {
        /*
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            System.out.println("Sleep failed");
        }
        */

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (LivingEntity entity : getTile(i, j).getLivingEntities()) {
                    entity.simulate(map);

                }
            }
        }
    }

    public int getId() {
        return id;
    }

    public static int getSize() {
        return size;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }
}
