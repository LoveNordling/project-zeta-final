package org.primal.map;

import org.primal.entity.Animal;
import org.primal.entity.Giraffe;
import org.primal.entity.Hyena;
import org.primal.entity.Lion;
import org.primal.entity.MankettiTree;
import org.primal.entity.Plant;
import org.primal.entity.UmbrellaTree;
import org.primal.entity.Zebra;
import org.primal.tile.DirtTile;
import org.primal.tile.LandTile;
import org.primal.tile.SandTile;
import org.primal.tile.Tile;
import org.primal.tile.WaterTile;
import org.primal.util.Vec2D;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.awt.geom.Line2D.linesIntersect;

/**
 * Map holds a 2d array of chunks and handles the logic for spawning entities in the different chunks.
 */
public class Map {

    public int width;
    public AtomicInteger entityId = new AtomicInteger(0);
    private int mapSize;
    private int chunkSize;
    private Chunk[][] chunks;
    private ArrayList<Vec2D[]> waterCorners = new ArrayList<>();

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

        for (int i = 0; i < mapSize / Math.pow(5, 2); i++) {
            addWaterTiles();
        }

        for (int i = 0; i < mapSize / Math.pow(3, 2); i++) {
            addSandTiles();
        }

        for (int i = 0; i < mapSize / Math.pow(4, 2); i++) {
            addDirtTiles();
        }

        for (int i = 0; i < mapSize / Math.pow(2, 2); i++) {
            addAnimals();
        }

        for (int i = 0; i < mapSize / Math.pow(1, 2); i++) {
            addUmbrellaTrees();
        }

        for (int i = 0; i < mapSize / Math.pow(1, 2); i++) {
            addMankettiTrees();
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
    public Chunk getChunk(double x, double y) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                Vec2D chunkPosition = chunks[i][j].getPosition();
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
    public Vec2D checkCollision(double x, double y, Vec2D newPos, Vec2D dir) {
        if (x <= 0) {
            return new Vec2D(1, 0);
        } else if (y <= 0) {
            return new Vec2D(0, 1);
        } else if (x >= mapSize) {
            return new Vec2D(-1, 0);
        } else if (y >= mapSize) {
            return new Vec2D(0, -1);
        } else {
            return checkWaterCollision(x, y, newPos, dir);
        }
    }

    /**
     * Checks if new position is within a water area.
     * If it is within water area directional points out of water area are returned.
     * Otherwise direction does not change and (0,0) is returned.
     *
     * @param x         Current x coordinate.
     * @param y         Current y coordinate.
     * @param newPos    New suggested position.
     * @param direction Movement direction of animal.
     * @return (0, 0) if not in water area, else points in opposite direction.
     */
    public Vec2D checkWaterCollision(double x, double y, Vec2D newPos, Vec2D direction) {
        double newX = newPos.getX();
        double newY = newPos.getY();

        for (Vec2D[] cornerPairs : waterCorners) {
            Vec2D ul = cornerPairs[0];
            Vec2D lr = cornerPairs[1];
            if (x >= ul.getX() && x <= lr.getX() && y >= ul.getY() && y <= lr.getY()) {
                // A fix for if animal is already in the water area.
                x -= direction.getX() * 10;
                y -= direction.getY() * 10;

                if (linesIntersect(ul.getX(), ul.getY(), ul.getX(), lr.getY(), x, y, newX, newY)) {
                    return new Vec2D(-1, 0);
                } else if (linesIntersect(lr.getX(), ul.getY(), lr.getX(), lr.getY(), x, y, newX, newY)) {
                    return new Vec2D(1, 0);
                } else if (linesIntersect(ul.getX(), lr.getY(), lr.getX(), lr.getY(), x, y, newX, newY)) {
                    return new Vec2D(0, 1);
                } else if (linesIntersect(ul.getX(), ul.getY(), lr.getX(), ul.getY(), x, y, newX, newY)) {
                    return new Vec2D(0, -1);
                }
            }
        }
        return new Vec2D(0, 0);
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
     * Saves upper left and the lower right corner for collision detection.
     * Then iterates through the tiles and replaces the chosen ones with water tiles.
     */
    private void addWaterTiles() {
        Random generator = new Random();
        int randX = generator.nextInt(mapSize) + 1;
        int randY = generator.nextInt(mapSize) + 1;
        int waterWidth = generator.nextInt(30) + 10;

        Vec2D[] cornerPairs = new Vec2D[2];
        // Upper left corner
        cornerPairs[0] = new Vec2D(randX - waterWidth, randY - waterWidth);
        // Lower right corner
        cornerPairs[1] = new Vec2D(randX + waterWidth + 1, randY + waterWidth + 1);
        waterCorners.add(cornerPairs);

        ArrayList<Tile> tiles = getTiles(randX, randY, waterWidth);
        for (Tile tile : tiles) {
            if (tile instanceof LandTile) {
                replaceTile(tile, new WaterTile(tile.getX(), tile.getY(), this));
            }
        }
    }

    private void addSandTiles() {
        Random generator = new Random();
        int randX = generator.nextInt(mapSize) + 1;
        int randY = generator.nextInt(mapSize) + 1;
        int sandWidth = generator.nextInt(10) + 5;

        ArrayList<Tile> tiles = getTiles(randX, randY, sandWidth);
        for (Tile tile : tiles) {
            if (tile instanceof LandTile) {
                replaceTile(tile, new SandTile(tile.getX(), tile.getY(), this));
            }
        }
    }

    private void addDirtTiles() {
        Random generator = new Random();
        int randX = generator.nextInt(mapSize) + 1;
        int randY = generator.nextInt(mapSize) + 1;
        int sandWidth = generator.nextInt(10) + 5;

        ArrayList<Tile> tiles = getTiles(randX, randY, sandWidth);
        for (Tile tile : tiles) {
            if (tile instanceof LandTile) {
                replaceTile(tile, new DirtTile(tile.getX(), tile.getY(), this));
            }
        }
    }

    private void replaceTile(Tile old, Tile replacer) {
        Chunk chunk = getChunk((int) old.getX() / chunkSize, (int) old.getY() / chunkSize);
        Tile[][] tiles = chunk.getTiles();
        for (int z = 0; z < 16; z++) {
            for (int w = 0; w < 16; w++) {
                if (tiles[z][w].equals(old)) {
                    tiles[z][w] = replacer;
                }
            }
        }
    }

    /**
     * spawnLion spawns a lion on the tile tile
     *
     * @param tile the tile for the lion to be spawned upon
     */
    public void spawnLion(Tile tile) {
        Lion lion = new Lion(tile.getX(), tile.getY(), this, 100.0f, 100.0f);
        tile.addLivingEntity(lion);
    }

    /**
     * spawnZebra spawns a zebra on the tile tile
     *
     * @param tile the tile for the zebra to be spawned upon
     */
    public void spawnZebra(Tile tile) {
        Zebra zebra = new Zebra(tile.getX(), tile.getY(), this, 100.0f, 100.0f);
        tile.addLivingEntity(zebra);
    }

    /**
     * spawnLion spawns amount amount of lions on the tile tile
     *
     * @param tile   the tile for the lions to be spawned upon
     * @param amount the amount of lions to be spawned
     */
    public void spawnLion(Tile tile, int amount) {
        for (int i = 0; i < amount; i++) {
            Lion lion = new Lion(tile.getX(), tile.getY(), this, 100.0f, 100.0f);
            tile.addLivingEntity(lion);
        }
    }

    /**
     * spawnGiraffe spawns a giraffe on the tile tile
     *
     * @param tile the tile for the giraffe to be spawned upon
     */
    public void spawnGiraffe(Tile tile) {
        System.out.println("Y");
        Giraffe giraffe = new Giraffe(tile.getX(), tile.getY(), this, 100.0f, 100.0f);
        tile.addLivingEntity(giraffe);
        System.out.println("Z");
    }

    /**
     * spawnLion spawns amount amount of giraffes on the tile tile
     *
     * @param tile   the tile for the giraffes to be spawned upon
     * @param amount the amount of giraffes to be spawned
     */
    public void spawnGiraffe(Tile tile, int amount) {
        for (int i = 0; i < amount; i++) {
            Giraffe giraffe = new Giraffe(tile.getX(), tile.getY(), this, 100.0f, 100.0f);
            tile.addLivingEntity(giraffe);
        }
    }

    /**
     * Randomly selects a group of tiles with a random radius between 0 and 2.
     * Then a species is randomly chosen.
     * On each of the selected tiles one instance of the selected species is spawned.
     */
    public void addAnimals() {
        Random generator = new Random();
        int randX = generator.nextInt(mapSize) + 1;
        int randY = generator.nextInt(mapSize) + 1;
        int packWidth = generator.nextInt(3);
        int species = generator.nextInt(3);
        Animal animal;

        ArrayList<Tile> tiles = getTiles(randX, randY, packWidth);
        for (Tile tile : tiles) {
            if (tile.isLandTile()) {
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
     * On each of the selected tiles one Umbella tree is spawned.
     */
    public void addUmbrellaTrees() {
        Random generator = new Random();
        int randX = generator.nextInt(mapSize) + 1;
        int randY = generator.nextInt(mapSize) + 1;
        int forestWidth = generator.nextInt(3);

        ArrayList<Tile> tiles = getTiles(randX, randY, forestWidth);
        for (Tile tile : tiles) {
            if (tile instanceof LandTile) {
                float treeSize = generator.nextInt(2) + 1.5f;
                Plant plant = new UmbrellaTree(tile.getX(), tile.getY(), this, treeSize);
                tile.addLivingEntity(plant);
            }
        }
    }

    public void addMankettiTrees() {
        Random generator = new Random();
        int randX = generator.nextInt(mapSize) + 1;
        int randY = generator.nextInt(mapSize) + 1;
        int forestWidth = generator.nextInt(1);

        ArrayList<Tile> tiles = getTiles(randX, randY, forestWidth);
        for (Tile tile : tiles) {
            if (tile instanceof LandTile) {
                float treeSize = generator.nextInt(1) + 0.5f;
                Plant plant = new MankettiTree(tile.getX(), tile.getY(), this, treeSize);
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

    public int getChunkSize() {
        return chunkSize;
    }
}
