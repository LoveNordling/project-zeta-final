package org.primal.java.graphics;

import org.primal.java.Simulation;
import org.primal.java.map.Chunk;
import org.primal.java.map.Map;

public class UI {
    private Map map;

    public UI() {
        map = new Map();
        new Simulation(this, map);
    }

    public void setMap(Map map) {
        // TODO: only update map parts that are changed.
        this.map = map;
        render();
    }

    private void render() {
        for (Chunk chunk : map.getChunks()) {
            for (int x = 0; x < chunk.getSize(); x++) {
                for (int y = 0; y < chunk.getSize(); y++) {
                    chunk.getTile(x, y); // TODO: render tile.
                }
            }
        }
    }
}
