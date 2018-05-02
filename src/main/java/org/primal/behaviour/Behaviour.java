package org.primal.behaviour;

import org.primal.entity.Animal;
import org.primal.map.Map;

public class Behaviour {
    int weight = 0;
    Map map;
    Animal host;

    public Behaviour(Animal host, Map map) {
        this.map = map;
        this.host = host;
    }

    public void decide() {
        this.weight = 10;
    }

    public void act() {
        host.move();
    }

    public int getWeight() {
        return weight;
    }
}
