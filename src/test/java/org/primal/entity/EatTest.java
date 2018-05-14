package org.primal.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.primal.map.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EatTest {
    private Map map;
    private Lion lion1;
    private Lion lion2;
    private Giraffe giraffe;



    @BeforeEach
    public void init() {
        this.map = new Map(1);
        this.lion1 = new Lion(0, 0, map, 50, 50 );
        this.lion2 = new Lion(0, 0, map, 50, 50);
        this.giraffe = new Giraffe(0, 0, map, 100, 50);

    }

    @AfterEach
    public void destroy() {
        this.map = null;
        this.lion1 = null;
        this.lion2 = null;
        this.giraffe = null;
    }

    @Test
    public void eatTest() {
        lion1.eat(giraffe);
        assertEquals(lion1.getFullness(), 100);
    }

    @Test
    public void eatSameAnimalTest() {
        lion1.eat(giraffe);
        assertEquals(lion1.getFullness(), 100);

        lion2.eat(giraffe);
        assertEquals(lion2.getFullness(), 50);
    }

    @Test
    public void carnivoreEatingCarnivoreTest() {
        lion1.eat(lion2);
        assertEquals(lion1.getFullness(), 50);
    }

    @Test
    public void herbivoreEatingCarnivoreTest() {
        giraffe.eat(lion2);
        assertEquals(giraffe.getFullness(), 50);
    }


}
