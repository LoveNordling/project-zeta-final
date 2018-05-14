package org.primal.map;

import org.primal.SimObject;
import org.primal.entity.LivingEntity;
import org.primal.tile.LandTile;
import org.primal.tile.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Chunk extends SimObject {

    private Tile[][] tiles;
    private int size = 16;
    private int id;
    private boolean isFrozen = false;
    private BufferedImage image;
    private boolean isAnimated = false;

    /**
     * Creates a chunk object
     *
     * @param x   = x-coordinate
     * @param y   = y-coordinate
     * @param map = current map
     */
    public Chunk(float x, float y, Map map) {
        super(x, y, map);
        tiles = new Tile[size][size];
        image = new BufferedImage(size * 30, size * 30, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                float xPos = i + getX() * this.size;
                float yPos = j + getY() * this.size;
                Tile tile = new LandTile(xPos, yPos, map);
                tiles[i][j] = tile;
            }
        }
    }

    /**
     * Iterates over each pixel inside of a chunk and renders it to a BufferedImage.
     */
    public void renderImage() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < 30; k++) {
                    for (int l = 0; l < 30; l++) {
                        Color color = tiles[i][j].getColors()[k / 10][l / 10];
                        image.setRGB((i * 30) + k, (j * 30) + l, color.getRGB());
                    }
                }
            }
        }
    }

    public void decimate() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                getTile(i, j).slaughter();
            }
        }
    }

    public void antiDecimate() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                getTile(i, j).antiSlaughter();
            }
        }
    }

    /**
     * freeze sets the chunk's status to be frozen, meaning animals on the chunk wont move
     */
    public void freeze() {
        isFrozen = true;
    }

    /**
     * unfreeze the chunk so animals on the chunk can be moved
     */
    public void unfreeze() {
        isFrozen = false;
    }

    public boolean isAnimated() {
        return isAnimated;
    }

    public void setAnimated(boolean animated) {
        isAnimated = animated;
    }

    public void animate() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j].isAnimated()) {
                    tiles[i][j].animate();
                }
            }
        }
        renderImage();
    }

    public void printChunk() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (LivingEntity entity : getTile(i, j).getLivingEntities()) {
                    System.out.println(entity);
                }
            }
        }
    }

    /**
     * Called at a set worker interval and will update entities in the chunk.
     */
    public void updateChunk() {
        if (isFrozen) {
            return;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (LivingEntity entity : getTile(i, j).getLivingEntities()) {
                    entity.simulate();
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

    public BufferedImage getImage() {
        return image;
    }
}
