package org.primal.java.map;
import org.primal.java.SimObject;
import org.primal.java.tile.Tile;
import org.primal.java.tile.LandTile;

public class Chunk extends SimObject {
    protected Tile[][] tiles;
    
    public int size = 16;
    public Chunk(float x, float y){
        super(x,y);
        subPieces = new Tile[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                tiles[i][j] = new LandTile((float) i, (float) j);
            }
        }      
    }

    Tile getTile(float x, float y){
        return tiles[x][y];
    }

    
    
}
