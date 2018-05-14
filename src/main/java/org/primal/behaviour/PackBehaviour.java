package org.primal.behaviour;

import org.primal.entity.Animal;
import org.primal.entity.Herbivore;
import org.primal.entity.LivingEntity;
import org.primal.map.Map;
import org.primal.tile.Tile;
import org.primal.util.Vec2D;

import java.util.ArrayList;

public class PackBehaviour extends Behaviour {

    Vec2D nextPos;
    public PackBehaviour(Animal host, Map map){
        super(host, map);
    }

    public void decide() {
        this.nextPos = new Vec2D(host.getX(), host.getY());
        this.weight = 9;
        ArrayList<Tile> tiles = map.getTiles(host.getX(), host.getY(), 2);
        int numNeightbours = 0;
        int i = 0;
        for (Tile tile : tiles) {
            for (LivingEntity entity : tile.getLivingEntities()) {

                if (host.getClass().equals(entity.getClass())) {

                    numNeightbours += 1;
                    this.weight += (this.weight < 50) ? 10 : 0;

                    Vec2D posChange = entity.getPosition().randomRadius(2);
                    nextPos.plus(posChange);
                }
                i++;
            }
        }
        this.nextPos = numNeightbours > 0 ? nextPos.times(((double)1)/numNeightbours) : nextPos;

    }

    public void act() {
        Vec2D dir = nextPos.minus(host.getPosition());
        System.out.println(nextPos);
        System.out.println(host.getPosition());
        System.out.println(dir);
        dir = dir.normalize();
        System.out.println(dir);
        System.out.println(nextPos.getX() - host.getX() + " " + (nextPos.getY() - host.getY()) );
        host.setDirection(dir);
        host.move();
    }

}
