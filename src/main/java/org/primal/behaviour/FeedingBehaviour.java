package org.primal.behaviour;

import org.primal.entity.Animal;
import org.primal.entity.LivingEntity;
import org.primal.entity.UmbrellaTree;
import org.primal.map.Map;
import org.primal.tile.Tile;
import org.primal.util.Vec2D;

import java.util.ArrayList;

public class FeedingBehaviour extends Behaviour {

    protected Vec2D chaseDir;
    private boolean decided = false;
    private UmbrellaTree selectedPlant;

    /**
     * Behaviour used for herbivores to search for food. It will check surrounding tiles and see if there is tree nearby. If
     * that is true, then it will move towards it and eat if it is withing reach.
     *
     * @param host = the herbivore who will have this behaviour
     * @param map  = the current Map.
     */

    public FeedingBehaviour(Animal host, Map map) {
        super(host, map);
    }

    /**
     * The method used to decide whether to prioritize this behaviour. Unless the animal has already decided which tree to eat from
     * it will check it's surrounding and then select a tree, setting the weight according to hunger. If it does not find a tree
     * the weight is set to 0.
     */
    public void decide() {
        if (!decided) {
            ArrayList<Tile> tiles = map.getTiles(host.getX(), host.getY(), 3);
            for (Tile tile : tiles) {
                for (LivingEntity entity : tile.getLivingEntities()) {
                    if (entity.isPlant()) {
                        this.weight = Math.round(100 - host.getFullness());
                        this.chaseDir = new Vec2D(entity.getX() - host.getX(), entity.getY() - host.getY());
                        chaseDir = chaseDir.normalize();

                        selectedPlant = (UmbrellaTree) entity;
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

    /**
     * Method used to act upon this decision if it was chosen. If the animal is within reach then it will eat from it. Otherwise
     * it will move closer.
     */
    public void act() {
        if (selectedPlant.getX() - host.getX() < 0.3 && selectedPlant.getY() - host.getY() < 0.3) {
            host.eat(selectedPlant);
            System.out.println(host + " ate some leaves from a " + selectedPlant);
            decided = false;
            selectedPlant = null;
        } else {
            host.setDirection(this.chaseDir);
            host.move();
        }

    }

}
