package org.primal.java.map;

import org.primal.java.SimObject;
import org.primal.java.tile.LandTile;
import org.primal.java.tile.Tile;

class Chunk<T extends SimObject> extends SimObject {
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

    Tile getTile(int x, int y) {
        return tiles[x][y];
    }
}
