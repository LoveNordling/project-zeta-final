package org.primal.map;

import org.primal.tile.Tile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class Map {
    private LinkedList<Chunk> megaChunks;
    private int mapSize;
    private int chunkSize;
    private Chunk[][] chunks;
    public int width;
    public AtomicInteger entityId;

    public Map(int width) {
        entityId = new AtomicInteger(0);
        this.width = width;
        chunks = new Chunk[width][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                chunks[x][y] = (new Chunk(x, y, this));
            }
        }
        chunkSize = Chunk.getSize();
        mapSize = width * chunkSize;

    }

    public LinkedList<Chunk> getMegaChunks() {
        return megaChunks;
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

    public void setChunks(Chunk[][] chunks) {
        this.chunks = chunks;
    }

    public Chunk getChunk(int x, int y) {
        return chunks[x][y];
    }

    public Tile getTile(float x, float y) {

        int xInt = (int) x;
        int yInt = (int) y;
        System.out.println(xInt + " : " + yInt);
        Chunk ch = this.getChunk(xInt / chunkSize, yInt / chunkSize);
        System.out.println(xInt/chunkSize + " : " + yInt/chunkSize);
        Tile t = ch.getTile(xInt % chunkSize, yInt % chunkSize);
        System.out.println(xInt%chunkSize + " : " + yInt/chunkSize);

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for(int i = 0; i < stackTraceElements.length; i++) {
            System.out.println(stackTraceElements[i]);
        }
        return t;
    }

    public boolean withinBounds(float x, float y) {
        if (x >= 0 && y >= 0 && x < mapSize && y < mapSize) {
            return true;
        }
        return false;
    }

    public ArrayList<Tile> getTiles(float x, float y, int radius) {
        ArrayList<Tile> tiles = new ArrayList<>();
        Tile currentTile;

        for (int i = -radius; i < (radius++); i++) {
            for (int j = -radius; j < (radius++); i++) {
                if (withinBounds(x + i, y + j)) {
                    currentTile = this.getTile(x + i, y + j);
                    tiles.add(currentTile);
                }
            }
        }
        return tiles;
    }

    public int getSize() {
        return mapSize;
    }
}
