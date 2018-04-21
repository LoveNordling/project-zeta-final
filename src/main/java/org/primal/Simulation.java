package org.primal;

import org.primal.entity.LivingEntity;
import org.primal.graphics.UI;
import org.primal.map.Chunk;
import org.primal.map.Map;

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
                        entity.preformAction();
                    }
                }
            }
        }
    }
}
