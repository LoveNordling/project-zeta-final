package org.primal.map;

import org.primal.SimObject;
import org.primal.entity.Lion;
import org.primal.entity.LivingEntity;
import org.primal.tile.LandTile;
import org.primal.tile.Tile;

import java.util.Timer;
import java.util.TimerTask;

public class Chunk extends SimObject implements Runnable {
    private Tile[][] tiles;
    private int size = 16;
    private int id;

    public Chunk(float x, float y, int id) {
        super(x, y);
        this.id = id;
        tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Lion lion = new Lion(i, j, 100.0f, 100.0f);
                Tile tile = new LandTile((float) i, (float) j);
                tile.addLivingEntity(lion);
                tiles[i][j] = tile;
            }
        }
    }

    public void run() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        for (LivingEntity entity : getTile(i, j).getLivingEntities()) {
                            entity.getShape().setLayoutX(10 + entity.getShape().getLayoutX());
                        }
                    }
                }
            }

        }, 0, 1000);
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
}
