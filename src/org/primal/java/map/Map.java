package org.primal.java.map;

import java.util.LinkedList;
import org.primal.java.tile.Tile;

public abstract class Map{
    protected LinkedList<Chunk<Chunk<Tile>>> megaChunks;
    
    public Map(){
        
    }
    
}
