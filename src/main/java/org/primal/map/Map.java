package org.primal.map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.primal.tile.Tile;

public class Map {
    private LinkedList<Chunk> megaChunks;
    private List<Chunk> chunks = new ArrayList<>();
    private int mapSize;
    private int chunkSize;

    public Map(int width) {
        int ChunkSize;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                chunks.add(new Chunk(x, y, chunks.size(), this));
            }
        }
        chunkSize = 16;
        mapSize = width*chunkSize;

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
    public Chunk getChunk(float x, float y){
        for(Chunk ch : chunks){
            float [] chunkPosition = ch.getPosition();
            if(chunkPosition[0] == x && chunkPosition[1] == y){
                return ch;
            }
        }
        return null;
    }

    public Tile getTile(float x, float y){
        int xInt = (int) x;
        int yInt = (int) y;
        System.out.println("" + y + "" + x + "");
        Chunk ch  = this.getChunk(xInt/chunkSize, yInt/chunkSize);
        return ch.getTile(xInt % chunkSize, yInt % chunkSize);
    }

    public boolean withinBounds(float x, float y){
        if(x > 0 && y > 0 && x < mapSize && y < mapSize){
            return true;
        }
        return false;
    }

    public ArrayList <Tile> getTiles(float x, float y, int radius){
        ArrayList <Tile> tiles = new ArrayList<>();
        Tile currentTile;
        
        for(int i = -radius; i<(radius++); i++){
            for(int j = -radius; j<(radius++); i++){
                if(withinBounds(x +i, y +j)){
                    currentTile = this.getTile(x + i, y + j);
                    tiles.add(currentTile);
                }
            }   
        }

        return tiles;
    }
    public int getSize(){
        return mapSize;
    }
}
