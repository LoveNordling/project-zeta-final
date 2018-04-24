package org.primal.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LionTest {
    private Lion lion;

    @BeforeEach
    private void spawnLion() {
        lion = new Lion(0, 0, 100.0f, 100.0f, null);
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
    }
}