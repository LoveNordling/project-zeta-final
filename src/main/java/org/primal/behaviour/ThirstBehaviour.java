package org.primal.behaviour;

import org.primal.entity.Animal;
import org.primal.map.Map;
import org.primal.tile.Tile;
import org.primal.tile.WaterTile;
import org.primal.util.Vec2D;

import java.util.ArrayList;

public class ThirstBehaviour extends Behaviour {
    WaterTile selectedTile;
    boolean decided = false;
    private Vec2D waterDir;

    /**
     * Creates a new behaviour that checks if the animal is thirsty.
     * @param host = the animal who will have this behaviour.
     * @param map = the current Map.
     */
    public ThirstBehaviour(Animal host, Map map) {
        super(host, map);
    }

    /**
     * Decides whether or not the animal is thirsty.  It does this by looking around if there are any water tiles in
     * it's surrounding. If there are, then it checks against the animals thirst level and sets the weight accordingly.
     * Otherwise the weight gets set to 0.
     */
    public void decide() {
        if (!decided) {
            ArrayList<Tile> tiles = map.getTiles(host.getX(), host.getY(), 3);
            for (Tile tile : tiles) {
                if (!tile.isLandTile()) {
                    decided = true;
                    selectedTile = (WaterTile) tile;
                    this.waterDir = new Vec2D(selectedTile.getX() - host.getX(), selectedTile.getY() - host.getY());
                    waterDir = waterDir.normalize();

                    this.weight = Math.round(100 - host.getThirst()); //temp
                    return;
                }
            }
            this.weight = 0;

        } else {
            this.weight = Math.round(100 - host.getFullness());
            this.waterDir = new Vec2D(selectedTile.getX() - host.getX(), selectedTile.getY() - host.getY());
            waterDir = waterDir.normalize();
        }
    }

    /**
     * Acts on this behaviour. If the animal is close enough to the water, it will drink from it.
     * Otherwise it will move towards it.
     */

    public void act() {
        if (selectedTile.getX() - host.getX() <= 0.5 && selectedTile.getY() - host.getY() <= 0.5) {
            selectedTile = null;
            decided = false;

            host.drink();
            System.out.println(host + " just drank some water.");
        }
        else {
            this.weight = Math.round(100 - host.getFullness());
            host.setDirection(waterDir);
            host.move();
        }
    }
}
