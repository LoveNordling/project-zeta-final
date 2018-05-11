package org.primal.entity.memory;

import org.primal.tile.Tile;
import org.primal.util.Vec2D;
import org.primal.entity.MemoryCell;
import org.primal.entity.MemoryType;

import java.util.Set;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class Memory {

	private Set<MemoryCell> memoryCells;
	
	private MemoryCell last;

	private Animal animal;

	public Memory(Animal animal, int size) {
		this.animal = animal;
		this.memorySize = size;
	}

	public boolean remember(Tile tile, Collection<MemoryType> type) {
		MemoryCell toRemember = new MemoryCell(tile,type);
		if(memoryCells.contains(toRemember)) {
			return false;
		} else {
			if(memoryCells.size() >= memorySize) {
				memoryCells.remove(last);
				last = toRemember;
				return memoryCells.add(toRemember);
			} else {
				last = toRemember;
				return MemoryCells.add(toRemember);
			}
		}
	}

	public Vec2D recallPosition(MemoryType type){

	}

}