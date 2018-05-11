package org.primal.entity.memory;

import org.primal.tile.Tile;
import org.primal.util.Vec2D;
import org.primal.entity.memory.MemoryCell;
import org.primal.entity.memory.MemoryType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Defines a {@code Memory} based on {@code MemoryCells}
 * The {@code MemoryCells} are stored internally in a {@code Arraylist}
 *
 * @see org.primal.entity.memory.MemoryCell
 * @see org.primal.entity.memory.MemoryType
 * @see Java.util.ArrayList
 */
public class Memory {

	// Houses the MemoryCells that make up this Memory
	private ArrayList<MemoryCell> memoryCells;

	/**
	 * Creates a new, empty {@code Memory}
	 *
	 */
	public Memory() {
	}

	/**
	 * Adds a new {@code MemoryCell} to this {@code Memory}
	 * Will return {@code False} if this {@code Memory} already contains the resulting {@code MemoryCell} as defined by {@code ArrayList.contains(Object o)}
	 *
	 * @param tile - The {@code Tile} to remember
	 * @param type - A {@code Collection} of {@code MemoryType}s to associate with this {@code Memory}
	 * @return {@code True} if a {@code MemoryCell} was added to this {@code Memory}
	 * @see org.primal.entity.MemoryCell#equals()
	 * @see java.util.ArrayList#contains()
	 */
	public boolean remember(Tile tile, Collection<MemoryType> type) {
		//TODO Optimize?
		MemoryCell toRemember = new MemoryCell(type,tile);
		if(memoryCells.contains(toRemember)) {
			return false;
		} else {
			return this.memoryCells.add(toRemember);
		}
	}

	/**
	 * 
	 */
	public Vec2D recallPosition(MemoryType type){
		//TODO Optimize?
		for (MemoryCell MC : this.memoryCells) {
			if(MC.contains(type)) {
				return MC.getTilePos();
			}
		}
		return null;
	}

}