package org.primal.map;

import org.primal.SimObject;
import org.primal.entity.Animal;
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
    private Map map;

    public Chunk(float x, float y, int id, Map map) {
        super(x, y);
        this.map = map;
        this.id = id;
        tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Lion lion = new Lion(i, j, 100.0f, 100.0f, map);
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
                            if (entity instanceof Animal) {
                                ((Animal) entity).performAction(map);
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
