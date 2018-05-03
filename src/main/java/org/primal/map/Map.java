package org.primal.map;

import org.primal.entity.Plant;
import org.primal.entity.Tree;
import org.primal.tile.Tile;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Map {
    private LinkedList<Chunk> megaChunks;
    private int mapSize;
    private int chunkSize;
    private Chunk[][] chunks;
    public int width;
    public AtomicInteger entityId = new AtomicInteger(0);

    public Map(int width) {
        this.width = width;
        chunks = new Chunk[width][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                chunks[x][y] = (new Chunk(x, y, this));
            }
        }
        chunkSize = 16;
        mapSize = width * chunkSize;

        for (int i = 0; i < mapSize / 2; i++) {
            addPlants();
        }
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

    public Chunk getChunk(float x, float y) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                Point2D chunkPosition = chunks[i][j].getPosition();
                if (x == chunkPosition.getX() && y == chunkPosition.getY()) {
                    return chunks[i][j];
                }
            }
        }
        return null;
    }

    public Tile getTile(float x, float y) {
        int xInt = (int) x;
        int yInt = (int) y;
        Chunk ch = this.getChunk(xInt / chunkSize, yInt / chunkSize);
        return ch.getTile(xInt % chunkSize, yInt % chunkSize);
    }

    public boolean withinBounds(float x, float y) {
        if (x >= 0 && y >= 0 && x < mapSize && y < mapSize) {
            return true;
        }
        return false;
    }

    private void addPlants() {
        Random generator = new Random();
        int randX = generator.nextInt(mapSize) + 1;
        int randY = generator.nextInt(mapSize) + 1;
        int forestWidth = generator.nextInt(3);

        ArrayList<Tile> tiles = getTiles(randX, randY, forestWidth);
        for (Tile tile : tiles) {
            // TODO: add check if tile already contains plant
            Plant plant = new Tree(tile.getX(), tile.getY(), this);
            tile.addLivingEntity(plant);
        }
    }

    public ArrayList<Tile> getTiles(float x, float y, int radius) {
        ArrayList<Tile> tiles = new ArrayList<>();
        Tile currentTile;

        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
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
