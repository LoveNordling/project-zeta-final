package org.primal.behaviour;

import org.primal.entity.Animal;
import org.primal.entity.LivingEntity;
import org.primal.entity.Plant;
import org.primal.map.Map;
import org.primal.tile.Tile;
import org.primal.util.Vec2D;

import java.util.ArrayList;

public class FeedingBehaviour extends Behaviour {
    protected Vec2D chaseDir;
    private boolean decided = false;
    private Plant selectedPlant;

    public FeedingBehaviour(Animal host, Map map) {
        super(host, map);
    }

    public void decide() {
        if (!decided) {
            ArrayList<Tile> tiles = map.getTiles(host.getX(), host.getY(), 3);
            for (Tile tile : tiles) {
                for (LivingEntity entity : tile.getLivingEntities()) {
                    if (entity.isPlant()) {
                        this.weight = Math.round(100 - host.getFullness());
                        this.chaseDir = new Vec2D(entity.getX() - host.getX(), entity.getY() - host.getY());
                        chaseDir = chaseDir.normalize();

                        selectedPlant = (Plant) entity;
                        decided = true;
                        return;
                    }
                }
            }
            this.weight = 0;
        } else {
            this.weight = Math.round(100 - host.getFullness());
            this.chaseDir = new Vec2D(selectedPlant.getX() - host.getX(), selectedPlant.getY() - host.getY());
            chaseDir = chaseDir.normalize();
        }
    }

    public void act() {
        if (selectedPlant.getX() - host.getX() < 0.3 && selectedPlant.getY() - host.getY() < 0.3) {
            host.eat(selectedPlant);
            decided = false;
            selectedPlant = null;
        } else {
            host.setDirection(this.chaseDir);
            host.move();
        }



    }

}
