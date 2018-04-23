package org.primal.map;

import org.primal.SimObject;
import org.primal.tile.LandTile;
import org.primal.tile.Tile;

public class Chunk extends SimObject implements Runnable {
    private Tile[][] tiles;
    private int size = 16;
    private int chunkId;

    public void run() {
        System.out.println("Running thread: " + chunkId);
        try {
            for (int i = 4; i > 0; i--) {
                System.out.println("Thread: " + chunkId + ", " + i);
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + chunkId + " interrupted.");
        }
        System.out.println("Thread " + chunkId + " exiting.");
    }

    public Chunk(float x, float y, int chunkId) {
        super(x, y);
        this.chunkId = chunkId;
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
