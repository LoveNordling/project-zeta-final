package org.primal.behaviour;

import org.primal.entity.Animal;
import org.primal.map.Map;
import org.primal.util.Vec2D;

import java.util.concurrent.ThreadLocalRandom;

public class Behaviour {

    int weight = 0;
    Map map;
    Animal host;

    /**
     * Creates a behaviour. A behaviour is a function where the weight of the behaviour is dependant on different parameters.
     * The most heavily weighted behaviour will be acted upon. This is used to create behaviours where animals chase each other
     * or look for food for example.
     * @param host = the animal who has this behaviour.
     * @param map = the current Map.
     */

    public Behaviour(Animal host, Map map) {
        this.map = map;
        this.host = host;
    }

    /**
     * The decide method sets the weight of the behaviour.
     */

    public void decide() {
        this.weight = 10;
    }

    /**
     * The act method acts upon the decided behaviour, carrying out the desired function.
     */

    public void act() {
        Vec2D position = host.getPosition();
        Vec2D direction = host.getDirection();

        float speed = host.getSpeed();
        double angleChange = Math.toRadians(ThreadLocalRandom.current().nextDouble(-20, 20));

        Vec2D newDir = direction;

        float newDirX = (float) newDir.getX() * (float) Math.cos(angleChange) - (float) newDir.getY() * (float) Math.sin(angleChange);
        float newDirY = (float) newDir.getX() * (float) Math.sin(angleChange) + (float) newDir.getY() * (float) Math.cos(angleChange);

        newDir = new Vec2D(newDirX, newDirY);
        host.setDirection(newDir);
        host.move();
    }

    /**
     * Returns the weight of the behaviour.
     * @return int The weight of the behaviour.
     */
    public int getWeight() {
        return weight;
    }
}
