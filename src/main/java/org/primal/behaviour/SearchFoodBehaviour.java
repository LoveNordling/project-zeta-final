package org.primal.behaviour;

import org.primal.entity.Animal;
import org.primal.map.Map;

public class SearchFoodBehaviour extends Behaviour {
    /**
     * This behaviour is used to look around the world.
     * @param host = animal who will have this behaviour
     * @param map = the current Map.
     */

    public SearchFoodBehaviour(Animal host, Map map) {
        super(host, map);
    }

    /**
     * Sets the weight to a constant value. If it has been chosen as the behaviour to act upon, then it will
     * call on the standard act() function. It will then simply move, looking for food.
     */
    public void decide() {
        this.weight = 70; //TEMP VALUE
    }
}
