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
    private BufferedImage[] images;
    private int amountImages = 3;
    private boolean isAnimated = false;
    private int index = 0;
    private int elapsed = 0;

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

        images = new BufferedImage[amountImages];

        for (int i = 0; i < amountImages; i++) {
            images[i] = new BufferedImage(size * 30, size * 30, BufferedImage.TYPE_INT_RGB);
        }

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
                for (int b = 0; b < amountImages; b++) {
                    tiles[i][j].animate();

                    for (int m = 0; m < 30; m++) {
                        for (int n = 0; n < 30; n++) {
                            Color color = tiles[i][j].getColors()[m / 10][n / 10];
                            images[b].setRGB((i * 30) + m, (j * 30) + n, color.getRGB());
                        }
                    }
                }
            }
        }
    }

    public boolean isAnimated() {
        return isAnimated;
    }

    public void setAnimated(boolean animated) {
        isAnimated = animated;
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
        if (isAnimated) {
            if (elapsed >= 100) {
                index++;
                if (index >= amountImages) {
                    index = 0;
                }
                elapsed = 0;
            }
            elapsed++;
        }

        return images[index];
    }
}
