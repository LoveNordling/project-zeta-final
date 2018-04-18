package org.primal.java;

import org.primal.java.entity.Animal;
import org.primal.java.entity.LivingEntity;
import org.primal.java.graphics.UI;
import org.primal.java.map.Chunk;
import org.primal.java.map.Map;

public class Simulation {
    private UI ui;
    private Map map;

    public Simulation(UI ui, Map map) {
        this.ui = ui;
        this.map = map;
        loop();
    }

    private void loop() {
        while (true) {
            animalLoop();
            updateGraphics();
        }
    }

    private void updateGraphics() {
        ui.setMap(map);
    }

    private void animalLoop() {
        for (Chunk chunk : map.getChunks()) {
            for (int x = 0; x < chunk.getSize(); x++) {
                for (int y = 0; y < chunk.getSize(); y++) {
                    for (LivingEntity entity : chunk.getTile(x, y).getLivingEntities()) {
                        if (entity instanceof Animal) {
                            ((Animal) entity).move();
                        }
                    }
                }
            }
        }
    }
}
