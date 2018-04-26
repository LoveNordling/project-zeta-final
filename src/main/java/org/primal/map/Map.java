package org.primal.map;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Map {
    private LinkedList<Chunk> megaChunks;
    private Chunk[][] chunks;
    public int width;
    public Map(int width) {
        this.width = width;
        chunks = new Chunk[width][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                chunks[x][y] = (new Chunk(x, y, this));
            }
        }

    }



    public LinkedList<Chunk> getMegaChunks() {

        return megaChunks;
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

    public void setChunks(Chunk[][] chunks) {
        this.chunks = chunks;
    }
}
