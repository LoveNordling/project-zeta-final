package org.primal.behaviour;

import org.primal.entity.Animal;
import org.primal.entity.Herbivore;
import org.primal.entity.LivingEntity;
import org.primal.map.Map;
import org.primal.tile.Tile;
import org.primal.util.Vec2D;

import java.util.ArrayList;

public class PackBehaviour extends Behaviour {

    Vec2D nextPos = new Vec2D(host.getX(), host.getY());
    public PackBehaviour(Animal host, Map map){
        super(host, map);
    }

    public void decide() {
        this.weight = 0;
        ArrayList<Tile> tiles = map.getTiles(host.getX(), host.getY(), 3);
        int numNeightbours = 0;
        for (Tile tile : tiles) {
            for (LivingEntity entity : tile.getLivingEntities()) {
                System.out.println("Debugg");
                if (this.getClass().equals(entity.getClass())) {
                    numNeightbours += 1;
                    this.weight += (this.weight < 50) ? 10 : 0;
                    Vec2D posChange = entity.getPosition().randomRadius(0.1);
                    nextPos.plus(posChange);
                }
            }
        }
        nextPos.times(1/numNeightbours);
        this.weight = 10;

    }

    public void act() {
        host.setDirection(nextPos.minus(host.getPosition()));
    }


}
