package org.primal.behaviour;

import java.lang.Math;
import java.util.concurrent.ThreadLocalRandom;
import org.primal.entity.Animal;
import org.primal.map.Map;
import org.primal.tile.Tile;


public class BreedingBehaviour extends Behaviour {
    public BreedingBehaviour(Animal host, Map map){
        super(host, map);
    }
    public void decide(){
        Tile t = map.getTile(host.getX(), host.getY());
        if(t.contains(host.getType(), 2)){
            weight = (int) ThreadLocalRandom.current().nextDouble(-1000, 12); // change this if you want the giraffes to take over the world
        }
        else{
            weight = 0;
        }

    }
    public void act(){
        host.breed();
        host.setLastAction("Experienced the miracle of child birth");
    }
}
