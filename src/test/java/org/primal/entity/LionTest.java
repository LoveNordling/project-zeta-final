package org.primal.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.System;
import org.primal.map.Map;
import org.primal.tile.Tile;
import org.primal.entity.Lion;
import java.awt.geom.Point2D;


class LionTest {
    private Lion lion;
    private Map map;
    @BeforeEach
    private void spawnLion() {
        map = new Map(4); 
        lion = new Lion(0, 0, 100.0f, 100.0f, map);
    }

    @Test
    public void newLion() {
        assertNotNull(lion);
    }

    @Test
    public void eat() {
    }

    @Test
    public void move() {
        for(int i = 0; i<1000; i++){
            Tile oldTile = map.getTile(lion.getX(), lion.getY());
            lion.move1Unit();
            Tile newTile = map.getTile(lion.getX(), lion.getY());
            if(newTile.getLivingEntities().size() != 1){
                //TODO add assert
                System.out.println("ERROR");
                System.out.println("ERROR");
                System.out.println("ERROR");
                System.out.println("ERROR");
                System.out.println("ERROR");
            }
            //System.out.println(newTile.getLivingEntities().size());
            //if(newTile == oldTile && lion.atEdge(map) != false){
            //  System.out.println("error");
            //  assertNotNull(null);
            //}
        }
    }
    
}
