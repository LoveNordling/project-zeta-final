package org.primal.java.map;
import org.primal.java.SimObject;
import org.primal.java.tile.Tile;
import org.primal.java.tile.LandTile;

public class Chunk<T extends SimObject> extends SimObject {
    protected T[][] subPieces;
    
    public int size = 16;
    public Chunk(float x, float y){
        super(x,y);

        /*
        subPieces = new T.getClass()[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                subPieces[i][j] = new T((float) i, (float) j);
            }
        }
        */
        
    }
    
}
