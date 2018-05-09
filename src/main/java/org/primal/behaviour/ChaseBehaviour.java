package org.primal.behaviour;

import org.primal.entity.LivingEntity;
import org.primal.tile.Tile;
import org.primal.entity.Animal;
import org.primal.map.Map;

import java.util.ArrayList;
import org.primal.entity.Herbivore;
import org.primal.util.Vec2D;

public class ChaseBehaviour extends Behaviour {
    protected Vec2D chaseDir;
    private boolean isChasing = false;
    private LivingEntity chasedAnimal;

    public ChaseBehaviour(Animal host, Map map) {

        super(host, map);
    }

    public void decide() {
        if (!isChasing) {
            ArrayList<Tile> tiles = map.getTiles(host.getX(), host.getY(), 3);
            for (Tile tile : tiles) {
                for (LivingEntity entity : tile.getLivingEntities()) {
                    if (entity instanceof Herbivore) {
                        this.isChasing = true;
                        this.chasedAnimal = entity;

                        this.weight = Math.round(100 - host.getFullness());
                        this.chaseDir = new Vec2D(entity.getX() - host.getX(), entity.getY() - host.getY());
                        chaseDir.normalize();

                        return;
                    }
                }
            }
            this.weight = 0;
        }
        else {
            this.weight = Math.round(100 - host.getFullness());
            this.chaseDir = new Vec2D(chasedAnimal.getX() - host.getX(), chasedAnimal.getY() - host.getY());
            chaseDir.normalize();

        }
    }


    public void act() {
        if (chasedAnimal == null) {
            System.out.println("Anima e null");
            isChasing = false;
            return;
        } else if (chasedAnimal.getX() - host.getX() < 0.3 && chasedAnimal.getY() - host.getY() < 0.3) {
            host.eat(chasedAnimal);
            System.out.println("Eaten!!");
            chasedAnimal = null;
            isChasing = false;
        } else {
            host.setDirection(this.chaseDir);
            host.move();
        }
    }
}
