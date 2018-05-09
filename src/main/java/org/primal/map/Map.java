package org.primal.map;

import org.primal.entity.*;
import org.primal.tile.LandTile;
import org.primal.tile.Tile;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Map holds a 2d array of chunks and handles the logic for spawning entities in the different chunks.
 */
public class Map {

    public int width;
    public AtomicInteger entityId = new AtomicInteger(0);
    private int mapSize;
    private int chunkSize;
    private Chunk[][] chunks;

    /**
     * Creates a map with width x width chunks and randomly adds water, plants and animals.
     *
     * @param width Width in amount of chunks.
     */
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

        for (int i = 0; i < mapSize / 4; i++) {
            addWaterTiles();
        }
        for (int i = 0; i < mapSize / 2; i++) {
            addAnimals();
        }
        for (int i = 0; i < mapSize / 2; i++) {
            addPlants();
        }
    }

    /**
     * Runs printChunk function (which prints all entities in chunk) for all chunks in map.
     */
    public void printAll() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                chunks[x][y].printChunk();
            }
        }
    }

    /**
     * Removes all entities in all chunks in map.
     */
    public void nuke() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                chunks[x][y].decimate();
            }
        }
    }

    /**
     * Adds all entities after having nuked the map.
     */
    public void antiNuke() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                chunks[x][y].antiDecimate();
            }
        }
    }

    /**
     * Returns chunk at position (x,y).
     *
     * @param x X value for chunk to get.
     * @param y Y value for chunk to get.
     * @return Chunk at position (x,y) if x and y is valid, else null.
     */
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

    /**
     * Returns tile at position (x,y).
     *
     * @param x X value for tile to get.
     * @param y Y value for tile to get.
     * @return Tile at position (x,y) if x and y is valid, else null.
     */
    public Tile getTile(float x, float y) {
        int xInt = (int) x;
        int yInt = (int) y;
        Chunk ch = this.getChunk(xInt / chunkSize, yInt / chunkSize);
        return ch.getTile(xInt % chunkSize, yInt % chunkSize);
    }

    /**
     * Checks if coordinates are within map boundaries,
     * if they are then directional points (0,0) are returned
     * else directional points to move in opposite direction are returned.
     *
     * @param x X value to check.
     * @param y Y value to check.
     * @return X and y points in direction to move.
     * (0,0) if within bounds, else points in opposite direction of initial x and y.
     */
    public Point2D checkCollision(float x, float y) {
        if (x <= 0) {
            return new Point2D.Float(1, 0);
        } else if (y <= 0) {
            return new Point2D.Float(0, 1);
        } else if (x >= mapSize) {
            return new Point2D.Float(-1, 0);
        } else if (y >= mapSize) {
            return new Point2D.Float(0, -1);
        } else {
            return new Point2D.Float(0, 0);
        }
    }

    /**
     * Checks if x and y are within map boundaries.
     *
     * @param x X value to check.
     * @param y Y value to check.
     * @return True if within bounds, else false.
     */
    public boolean withinBounds(float x, float y) {
        return (x >= 0 && y >= 0 && x < mapSize && y < mapSize);
    }

    /**
     * Randomly selects a group of tiles with a random radius between 1 and 2.
     * Then iterates through the tiles and replaces the chosen ones with water tiles.
     */
    private void addWaterTiles() {
        Random generator = new Random();
        int randX = generator.nextInt(mapSize) + 1;
        int randY = generator.nextInt(mapSize) + 1;
        int waterWidth = generator.nextInt(2) + 1;

        ArrayList<Tile> tiles = getTiles(randX, randY, waterWidth);
        for (Tile tile : tiles) {
            tile.changeToWaterTile();
        }
        Chunk[][] chunks = getChunks();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                chunks[x][y].changeToWaterTiles();
            }
        }
    }

    /**
     * Randomly selects a group of tiles with a random radius between 0 and 2.
     * Then a species is randomly chosen.
     * On each of the selected tiles one instance of the selected species is spawned.
     */
    private void addAnimals() {
        Random generator = new Random();
        int randX = generator.nextInt(mapSize) + 1;
        int randY = generator.nextInt(mapSize) + 1;
        int packWidth = generator.nextInt(3);
        int species = generator.nextInt(3);
        Animal animal;

        ArrayList<Tile> tiles = getTiles(randX, randY, packWidth);
        for (Tile tile : tiles) {
            if (tile instanceof LandTile) {
                if (species == 0) {
                    animal = new Lion(tile.getX(), tile.getY(), this, 100.0f, 100.0f);
                } else if (species == 1) {
                    animal = new Hyena(tile.getX(), tile.getY(), this, 100.0f, 100.0f);
                } else {
                    animal = new Giraffe(tile.getX(), tile.getY(), this, 100.0f, 100.0f);
                }
                tile.addLivingEntity(animal);
            }
        }
    }

    /**
     * Randomly selects a group of tiles with a random radius between 0 and 2.
     * On each of the selected tiles one tree is spawned.
     */
    private void addPlants() {
        Random generator = new Random();
        int randX = generator.nextInt(mapSize) + 1;
        int randY = generator.nextInt(mapSize) + 1;
        int forestWidth = generator.nextInt(3);

        ArrayList<Tile> tiles = getTiles(randX, randY, forestWidth);
        for (Tile tile : tiles) {
            if (tile instanceof LandTile) {
                float treeSize = generator.nextInt(2) + 1.5f;
                Plant plant = new Tree(tile.getX(), tile.getY(), this, treeSize);
                tile.addLivingEntity(plant);
            }
        }
    }

    /**
     * Returns tile at coordinate (x,y) and tiles surrounding it by specified radius.
     *
     * @param x      X coordinate of tile in center of chosen tiles.
     * @param y      Y coordinate of tile in center of chosen tiles.
     * @param radius Radius by which to retrieve neighbouring tiles.
     * @return ArrayList of the chosen tiles.
     */
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

    public Chunk[][] getChunks() {
        return chunks;
    }

    public int getSize() {
        return mapSize;
    }
}
