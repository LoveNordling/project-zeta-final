package org.primal.map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Map {
    protected LinkedList<Chunk> megaChunks;
    private List<Chunk> chunks = new ArrayList<>();

    public Map() {
        chunks.add(new Chunk(0, 0));
    }

    public List<Chunk> getChunks() {
        return chunks;
    }
}
