package org.primal.java;

import org.primal.java.entity.Animal;
import org.primal.java.tile.Tile;

import java.util.HashMap;

public class Simulation {
    private HashMap<Integer, Animal> animals = new HashMap<>();
    private static int mapSize = 16;
    private int map[][] = new int[mapSize][mapSize];
    private GRAPHICS graphics;

    public Simulation(GRAPHICS graphics) {
        this.graphics = graphics;
        init();
        start();
    }

    private void init() {
        initMap();
        initAnimals();
    }

    private void initMap() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = generateTile();
            }
        }
    }

    private void initAnimals() {
        Cow cow = new Cow();
        animals.add(cow.getId(), cow);
    }

    private Tile generateTile() {
    }

    private void loop() {
        while (true) {
            animalLoop();
            updateGraphics();
        }
    }

    private void updateGraphics() {
        this.graphics.sendInfo(map, animals);
    }

    private void animalLoop() {
        for (Animal a : animals.values()) {
            a.performAction();
        }
    }

    public void start() {
        loop();
    }
}
