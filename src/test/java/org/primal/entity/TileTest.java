package org.primal.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.primal.map.Map;
import org.primal.tile.LandTile;
import org.primal.tile.Tile;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {
    private LandTile tile;
    private Hyena hyena;
    private Zebra zebra;
    private Map map = new Map(1);

    @BeforeEach
    private void init() {
        tile = new LandTile(0f, 0f, map);
        hyena = new Hyena(0, 0, map, 0, 0);
        zebra = new Zebra(0,0,map,0,0);

    }

    @AfterEach
    private void destroy() {
        tile = null;
        hyena = null;
        zebra = null;
    }


    @Test
    public void noAnimalOnTile() {
        assertEquals(tile.getLivingEntities().size(), 0);
    }

    @Test
    public void oneAnimalOnTile() {
        tile.addLivingEntity(zebra);
        assertEquals(tile.getLivingEntities().size(), 1);
    }

    @Test
    public void twoAnimalsOnTile() {
        tile.addLivingEntity(zebra);
        tile.addLivingEntity(hyena);

        assertEquals(tile.getLivingEntities().size(), 2);
    }

    @Test
    public void removeAnimal() {
        tile.addLivingEntity(zebra);
        assertEquals(tile.getLivingEntities().size(), 1);

        tile.removeLivingEntity(zebra);
        assertEquals(tile.getLivingEntities().size(), 0);
    }
}
