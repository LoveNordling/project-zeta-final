package org.primal.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.primal.map.Map;
import org.primal.tile.Tile;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
class LionTest {

    private Lion lion;
    private Map map;

    @BeforeEach
    private void spawnLion() {
        map = new Map(4);
        lion = new Lion(0, 0, map, 100.0f, 100.0f, 100.0f);
        Tile t = map.getTile(lion.getX(), lion.getY());
        t.addLivingEntity(lion);
        
    }
    @AfterEach
    private void destroy() {
        this.map = null;
        this.lion = null;
    }

    @Test
    public void breedTest(){
        Lion lion2 = new Lion(0, 0, map, 100.0f, 100.0f, 100.0f);
        Tile t = map.getTile(lion.getX(), lion.getY());
        t.addLivingEntity(lion2);
        int amount  = t.amount(lion.getType());
        assertEquals(amount, 2);
        lion.breed();
        assertEquals(t.amount(lion.getType()), 3);
    }
    
    @Test
    public void newLion() {
        assertNotNull(lion);
    }

    @Test
    public void eat() {
    }

    
    public void printError() {
        System.out.println("ERROR");
        System.out.println("ERROR");
        System.out.println("ERROR");
        System.out.println("ERROR");
        System.out.println("ERROR");

    }

    @Test
    public void testWithinBounds() {
        if (map.withinBounds(-1.0f, 0.0f) || map.withinBounds(-1.0f, -1.0f) || map.withinBounds(0.0f, -1.0f)) {
            printError();
        }
        if (map.withinBounds((float) map.getSize(), 0.0f) || map.withinBounds((float) map.getSize(), (float) map.getSize()) || map.withinBounds(0.0f, (float) map.getSize())) {
            printError();
        }

        if (!(map.withinBounds(1.0f, 1.0f) && map.withinBounds((float) map.getSize() - 1, (float) map.getSize() - 1))) {
            printError();
        }
    }

    @Test
    public void testGetChunk() {
        for (float i = 0.0f; i < map.width; i++) {
            for (float j = 0.0f; j < map.width; j++) {
                System.out.println(map.getChunk(i, j).getX());
                System.out.println(map.getChunk(i, j).getY());
                if (map.getChunk(i, j).getX() != i || map.getChunk(i, j).getY() != j) {
                    //TODO add assert
                    printError();
                }
            }
        }
    }
    //outdated test
    /*
    @Test
    
    public void move() {
        for (int i = 0; i < 1000; i++) {
            Tile oldTile = map.getTile(lion.getX(), lion.getY());
            lion.move1Unit();
            Tile newTile = map.getTile(lion.getX(), lion.getY());
            if (newTile.size() != 1) {
                //TODO add assert
                System.out.println("animal amount :" + newTile.size());
                    //printError();
            }
            //System.out.println(newTile.getLivingEntities().size());
            //if(newTile == oldTile && lion.atEdge(map) != false){
            //  System.out.println("error");
            //  assertNotNull(null);
            //}
        }
        }*/

}
