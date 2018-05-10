package org.primal.map;

import org.primal.SimObject;
import org.primal.entity.LivingEntity;
import org.primal.tile.LandTile;
import org.primal.tile.Tile;
import org.primal.tile.WaterTile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Chunk extends SimObject {

    private Tile[][] tiles;
    private int size = 16;
    private int id;
    private boolean isFrozen = false;
    private BufferedImage image;


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

        //renderImage();
    }

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

    /** freeze sets the chunk's status to be frozen, meaning animals on the chunk wont move
     */
    public void freeze(){
        isFrozen = true;
    }

    /** unfreeze the chunk so animals on the chunk can be moved
     */
    public void unfreeze(){
        isFrozen = false;
    }
    public void printChunk(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (LivingEntity entity : getTile(i, j).getLivingEntities()) {
                    System.out.println(entity);
                }
            }
        }
    }

    public void updateChunk() {
        /*
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Sleep failed");
        }*/
        if(isFrozen){
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

    public void changeToWaterTiles() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (tiles[x][y].shouldChangeToWaterTile()) {
                    tiles[x][y] = new WaterTile(tiles[x][y].getX(), tiles[x][y].getY(), map);
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
