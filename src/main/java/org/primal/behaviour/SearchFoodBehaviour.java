package org.primal.behaviour;

import org.primal.entity.Animal;
import org.primal.map.Map;

public class SearchFoodBehaviour extends Behaviour {

    public SearchFoodBehaviour(Animal host, Map map) {
        super(host, map);
    }

    public void decide() {
        this.weight = 70; //TEMP VALUE
    }

    public void act() {
        host.move();
    }
}
