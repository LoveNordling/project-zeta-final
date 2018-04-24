package org.primal.tile;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.primal.SimObject;
import org.primal.entity.LivingEntity;

import java.util.LinkedList;
import java.util.List;


public class Tile extends SimObject {
    private List<LivingEntity> livingEntities;

    protected static int size = 30;
    public Tile(float x, float y) {
        super(x, y);
        this.livingEntities = new LinkedList<LivingEntity>();
        this.shape = new Rectangle(x*size, y*size, size, size);
        this.shape.setStroke(Color.BLACK);
        this.shape.setFill(Color.RED);
    }

    public Tile(float x, float y, List<LivingEntity> livingEntities) {
        super(x, y);
        this.livingEntities = livingEntities;
    }

    public void addLivingEntity(LivingEntity ent) {
        this.livingEntities.add(ent);
    }

    public void removeLivingEntity(LivingEntity ent) {
        if (this.livingEntities.contains(ent)) {
            this.livingEntities.remove(ent);
        }
    }

    public static int getSize() {
        return size;
    }

    public List<LivingEntity> getLivingEntities() {
        return livingEntities;
    }
}
