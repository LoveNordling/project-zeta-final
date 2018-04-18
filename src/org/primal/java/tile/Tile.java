package org.primal.java.tile;

import org.primal.java.SimObject;
import org.primal.java.entity.LivingEntity;

import java.util.List;
import java.util.LinkedList;


public class Tile extends SimObject {
    private List<LivingEntity> livingEntities;

    public Tile(float x, float y){
    	super(x,y);
    	this.livingEntities = new LinkedList<LivingEntity>();
    }

    public Tile(float x, float y, List<LivingEntity> livingEntities) {
        super(x, y);
        this.livingEntities = livingEntities;
    }

    public void addLivingEntity(LivingEntity ent){
    	this.livingEntities.add(ent);
    }

    public void removeLivingEntity(LivingEntity ent){
    	if(this.livingEntities.contains(ent)){
    		this.livingEntities.remove(ent);
    	}
    }

    public List<LivingEntity> getLivingEntities() {
        return livingEntities;
    }
}
