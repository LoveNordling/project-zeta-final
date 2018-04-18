package org.primal.java.map;

import java.util.LinkedList;
import org.primal.java.tile.Tile;

public abstract class Map{
    protected LinkedList<Chunk<Chunk<Tile>>> megaChunks;
    LinkedList chunks = new LinkedList();
    public Map(){
        chunks.append(new Chunk(0,0));
    }

    Tile getTile(float x, float y){
        return chunks(0).getTile(x,y);
    }
    
}
