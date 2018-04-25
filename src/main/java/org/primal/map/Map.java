package org.primal.map;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Map {
    private LinkedList<Chunk> megaChunks;
    private List<Chunk> chunks = new ArrayList<>();

    public Map(int width) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                chunks.add(new Chunk(x, y, chunks.size(), this));
            }
        }

    }

    public LinkedList<Chunk> getMegaChunks() {

        return megaChunks;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<Chunk> chunks) {
        this.chunks = chunks;
    }
}
