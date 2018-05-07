package org.primal.map;

import org.primal.SimObject;
import org.primal.entity.LivingEntity;
import org.primal.tile.LandTile;
import org.primal.tile.Tile;
import org.primal.tile.WaterTile;

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
    }

    public void changeToWaterTiles() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (tiles[x][y].shouldChangeToWaterTile()) {
                    tiles[x][y] = new WaterTile(tiles[x][y].getX(), tiles[x][y].getY(), map);
                }
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
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
