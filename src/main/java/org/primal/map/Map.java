package org.primal.map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Map {
    private LinkedList<Chunk> megaChunks;
    private List<Chunk> chunks = new ArrayList<>();

    public void setChunks(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    public LinkedList<Chunk> getMegaChunks() {

        return megaChunks;
    }

    public Map(int width) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                chunks.add(new Chunk(x, y, 0));
            }
        }

    }

    public List<Chunk> getChunks() {
        return chunks;
    }
}
