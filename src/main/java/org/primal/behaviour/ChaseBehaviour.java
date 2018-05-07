package org.primal.behaviour;

import org.primal.entity.LivingEntity;
import org.primal.tile.Tile;
import org.primal.entity.Animal;
import org.primal.map.Map;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import org.primal.entity.Herbivore;

public class ChaseBehaviour extends Behaviour {
    protected Point2D.Float chaseDir;
    public ChaseBehaviour(Animal host, Map map) {

        super(host, map);
    }

    public void decide() {
        ArrayList<Tile> tiles = map.getTiles(host.getX(), host.getY(), 1);
        for (Tile tile : tiles) {
            for (LivingEntity entity : tile.getLivingEntities()) {
                if (entity instanceof Herbivore) {
                    this.weight = Math.round(100 - host.getFullness());
                    this.chaseDir = new Point2D.Float(entity.getX() - host.getX(), entity.getY() - host.getY());
                    normalize(chaseDir);
                    return;
                }
            }
        }
    }

    protected Point2D normalize(Point2D p){
        double x = p.getX();
        double y = p.getY();
        float abs = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return new Point2D.Float((float) x/abs, (float) y / abs);
    }

    public void act() {
        host.setDirection(this.chaseDir);
        host.move();
    }
}
