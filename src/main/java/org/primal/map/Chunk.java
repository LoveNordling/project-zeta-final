package org.primal.map;

import org.primal.SimObject;
import org.primal.entity.*;
import org.primal.tile.LandTile;
import org.primal.tile.Tile;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

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
                LivingEntity entity = null;

                int n = ThreadLocalRandom.current().nextInt(0, 3);
                if (n == 0) {
                    entity = new Lion(i, j, 100.0f, 100.0f);
                } else if (n == 1) {
                    entity = new Hyena(i, j, 100.0f, 100.0f);
                } else if (n == 2) {
                    entity = new Giraffe(i, j, 100.0f, 100.0f);
                }

                Tile tile = new LandTile((float) i, (float) j);
                tile.addLivingEntity(entity);
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
                            if (entity instanceof Animal) {
                                ((Animal) entity).move();
                            }
                        }
                    }
                }
            }

        }, 0, 100);
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
