package org.primal.map;

import org.primal.SimObject;
import org.primal.tile.LandTile;
import org.primal.tile.Tile;

public class Chunk extends SimObject {
    private Tile[][] tiles;

    private int size = 16;

    Chunk(float x, float y) {
        super(x, y);
        tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = new LandTile((float) i, (float) j);
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
