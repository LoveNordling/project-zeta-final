package org.primal.java.map;

import org.primal.java.SimObject;
import org.primal.java.tile.LandTile;
import org.primal.java.tile.Tile;

public class Chunk extends SimObject {
    private Tile[][] tiles;

    private int size = 16;

    Chunk(float x, float y) {
        super(x, y);
        tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = new LandTile((float) i, (float) j, null);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }
}
