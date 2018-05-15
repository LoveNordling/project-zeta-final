package org.primal.entity.memory;

import org.primal.tile.Tile;
import org.primal.util.Vec2D;
import org.primal.entity.memory.MemoryCell;
import org.primal.entity.memory.MemoryType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Defines a {@code Memory} based on {@code MemoryCells}.
 * The {@code MemoryCells} are stored internally in a {@code Arraylist}.
 *
 * @see org.primal.entity.memory.MemoryCell
 * @see org.primal.entity.memory.MemoryType
 * @see Java.util.ArrayList
 */
public class Memory {

	// Houses the MemoryCells that make up this Memory
	private ArrayList<MemoryCell> memoryCells;

	/**
	 * Creates a new, empty {@code Memory}.
	 *
	 */
	public Memory() {
		memoryCells = new ArrayList<MemoryCell>();
	}


	/**
	 * Adds a new {@code MemoryCell} to this {@code Memory}.
	 *
	 * @param tile - The {@code Tile} to remember.
	 * @param type - A {@code Collection} of {@code MemoryType}s to associate with this {@code Memory}.
	 *
	 * @see org.primal.entity.MemoryCell#equals()
	 */
	public void remember(Tile tile, Collection<MemoryType> type) {
		//TODO Optimize?
		MemoryCell toRemember = new MemoryCell(tile,type);

		for(MemoryCell MC : memoryCells) {
			if(MC.hashCode() == toRemember.hashCode()) {
				memoryCells.remove(MC);
				break;
			}
		}

		this.memoryCells.add(toRemember);
	}

	/**
	 * Returns the {@code Vec2D} position of a {@code MemoryCell} with type {@code type}.
	 *
	 * @param type - A {@code MemoryType} to check for.
	 * @return       A {@code Vec2D} position of a {@code MemoryCell} with type {@code type} or {@code null} if none could be found.
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

	/**
	 * Returns the {@code Vec2D} position of a {@code MemoryCell} with the type {@code FOOD}.
	 *
	 * @return the {@code Vec2D} position of a {@code MemoryCell} with the type {@code FOOD}.
	 */
	public Vec2D recallPositionFood() {
		return recallPosition(MemoryType.FOOD);
	}

	/**
	 * Returns the {@code Vec2D} position of a {@code MemoryCell} with the type {@code WATER}.
	 *
	 * @return the {@code Vec2D} position of a {@code MemoryCell} with the type {@code WATER}.
	 */
	public Vec2D recallPositionWater() {
		return recallPosition(MemoryType.WATER);
	}

}