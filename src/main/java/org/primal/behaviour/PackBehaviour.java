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
    Vec2D dir;
    public PackBehaviour(Animal host, Map map){
        super(host, map);
    }

    public void decide() {

        this.nextPos = new Vec2D(host.getX(), host.getY());
        this.weight = 0;
        ArrayList<Tile> tiles = map.getTiles(host.getX(), host.getY(), 2);
        int numNeightbours = 0;
        int i = 0;
        for (Tile tile : tiles) {
            for (LivingEntity entity : tile.getLivingEntities()) {

                if (host.getClass().equals(entity.getClass())) {

                    numNeightbours += 1;
                    this.weight += (this.weight < 100) ? 20 : 0;

                    Vec2D targetPos = entity.getPosition();//).plus(entity.getDirection());


                    this.nextPos = this.nextPos.plus(targetPos);
                    //System.out.println("Found a neighbour");
                }
                i++;
            }
        }

        this.nextPos = numNeightbours > 0 ? nextPos.times(((double)1)/numNeightbours) : nextPos;
        nextPos = nextPos;//.randomRadius(10);
        Vec2D nextPosDir = nextPos.minus(host.getPosition());
        Vec2D orthogonalTargetPos = new Vec2D(nextPosDir.getY(), -nextPosDir.getX());
        this.dir = orthogonalTargetPos;//host.getDirection().project(orthogonalTargetPos);
    }

    public void act() {

        //Vec2D dir = nextPos.minus(host.getPosition());
        //Vec2D dir = host.getPosition().minus(nextPos);
        //System.out.println("\n" + nextPos);
        //System.out.println(host.getPosition());
        System.out.println("\n" + nextPos);
        System.out.println(host.getPosition());

        dir = dir.normalize();
        System.out.println(dir);
        //host.setDirection(dir.mean(host.getDirection()));
        host.setDirection(dir);
        host.move();

    }
}
