package org.primal.java.map;

import org.primal.java.tile.Tile;

import java.util.LinkedList;

public abstract class Map {
    protected LinkedList<Chunk<Chunk<Tile>>> megaChunks;
    private LinkedList chunks = new LinkedList();

    public Map() {
        chunks.add(new Chunk<>(0, 0));
    }

    public LinkedList getChunks() {
        return chunks;
    }
}
