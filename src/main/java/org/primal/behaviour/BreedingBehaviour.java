package org.primal.behaviour;

import java.lang.Math;
import java.util.concurrent.ThreadLocalRandom;
import org.primal.entity.Animal;
import org.primal.map.Map;
import org.primal.tile.Tile;


public class BreedingBehaviour extends Behaviour {
    /**
     * Creates a breeding behaviour.
     * @param host = the animal who will have this behaviour.
     * @param map = the current Map.
     */
    public BreedingBehaviour(Animal host, Map map){
        super(host, map);
    }

    /**
     * Method used to set the weight of this behaviour. It checks whether two of the same type of animals are currently on
     * the same tile. If this is true then it sets the weight to a random value. Otherwise the weight is 0.
     */
    public void decide(){
        Tile t = map.getTile(host.getX(), host.getY());
        if(t.contains(host.getType(), 2)){
            weight = (int) ThreadLocalRandom.current().nextDouble(-1000, 12); // change this if you want the giraffes to take over the world
        }
        else{
            weight = 0;
        }
    }

    /**
     * Creates a new instance of the animal
     */
    public void act(){
        host.breed();
    }
}
