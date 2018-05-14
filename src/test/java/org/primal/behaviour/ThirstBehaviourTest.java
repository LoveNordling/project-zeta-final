package org.primal.behaviour;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals; //float
import org.primal.entity.Giraffe;
import org.primal.map.Map;

public class ThirstBehaviourTest {
    private Map map;
    private Giraffe giraffe;



    @BeforeEach
    private void init() {
        this.map = new Map(1);
        this.giraffe = new Giraffe(0, 0, map, 100, 50, 50);

    }

    @AfterEach
    private void destroy() {
        this.map = null;
        this.giraffe = null;
    }

    @Test
    private void drinkTest() {
        giraffe.drink();
        assertEquals(giraffe.getThirst(), 100);
    }

    


}
