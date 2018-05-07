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
    private Map map;

    public Chunk(float x, float y, Map map) {
        super(x, y);
        this.map = map;
        tiles = new Tile[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                float xPos = i + getPosition()[0] * this.size;
                float yPos = j + getPosition()[1] * this.size;
                LivingEntity entity = null;

                int n = ThreadLocalRandom.current().nextInt(0, 3);
                if (n == 0) {
                    entity = new Lion(xPos, yPos, 100.0f, 100.0f, map, map.entityId.incrementAndGet());
                } else if (n == 1) {
                    entity = new Hyena(xPos, yPos, 100.0f, 100.0f, map, map.entityId.incrementAndGet());
                } else if (n == 2) {
                    entity = new Giraffe(xPos, yPos, 100.0f, 100.0f, map, map.entityId.incrementAndGet());
                }
                Tile tile = new LandTile(xPos, yPos);
                tile.addLivingEntity(((Animal) entity).getId(), entity);
                tiles[i][j] = tile;
            }
        }
    }
    public void decimate(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                getTile(i, j).slaughter();
            }
        }
    }
    public void antiDecimate(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                getTile(i, j).antiSlaughter();
            }
        }
    }
    public void printChunk(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (LivingEntity entity : getTile(i, j).getLivingEntities().values()) {
                    System.out.println(entity);
                }
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
                for (LivingEntity entity : getTile(i, j).getLivingEntities().values()) {
                    entity.simulate(map);
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
