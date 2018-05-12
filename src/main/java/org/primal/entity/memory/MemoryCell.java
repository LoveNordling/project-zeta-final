package org.primal.entity.memory;

import org.primal.util.Vec2D;
import org.primal.tile.Tile;
import org.primal.entity.memory.MemoryType;

import java.util.Set;
import java.util.EnumSet;
import java.util.Collection;

/**
 * A {@code MemoryCell} hold information about a specific {@code Tile} that an {@code Animal} remembers
 * This Object is Immutable.
 *
 * @see org.primal.tile.Tile
 */
public class MemoryCell {


	/*
	 * Stores all the types this MemoryCell has.
	 * While this could technically be stored in an Array instead of an EnumSet, the EnumSet gives extra
	 * functionallity that Array doesn't provide.
	 *
	 * More specifically, the performance gain from EnumSet and the guarantees inherited from Set.
	 */
	private final Set<MemoryType> types;

	/*
	 * The Position on the Tile this MemoryCell is associated with.
	 * We store the position rather than the Tile because the Tile is mutable.
	 */
	private final Vec2D tilePos;


	// hashCode() uses Lazy initialization
	private int hashCode;

	/*
	 * Uncomment the block below to enable mutation of this class
	 * Remember that you can not cache the hashcode if you do, and that it needs to be recalculated on every call.
	 */

	// /**
	//  * Creates a new {@code MemoryCell}
	//  *
	//  * @param tile - The {@code Tile} to be associated with this {@code MemoryCell}
	//  * @return A new empty {@code MemoryCell} associated with {@code tile}
	//  */
	// public MemoryCell(Tile tile) {
	// 	this.types = EnumSet.noneOf(MemoryType.class);
	// 	this.tilePos = tile.getPosition();
	// }

	/**
	 * Creates a new {@code MemoryCell} containing all the elements in {@code types}
	 *
	 * @param types - Collection of types to be added to this {@code MemoryCell}
	 * @param tile  - The {@code Tile} to be associated with this {@code MemoryCell}
	 * @return        A new {@code MemoryCell} associated with {@code tile} containing all the elements of {@code types}
	 */
	public MemoryCell(Tile tile, Collection<MemoryType> types)  {
		this.types = EnumSet.copyOf(types);
		this.tilePos = tile.getPosition();
	}

	/**
	 * Returns {@code True} if this {@code MemoryCell} contains the specified {@code MemoryType}
	 *
	 * @param type -  A {@code MemoryType}
	 * @return {@code True} if this {@code MemoryCell} contains the specified {@code MemoryType}
	 */
	public boolean contains(MemoryType type) {
		return this.types.contains(type);
	}

	/*
	 * Uncomment the blocks below to enable mutation of this class
	 * Remember that you can not cache the hashcode if you do, and that it needs to be recalculated on every call.
	 */

	// /**
	//  * Adds the specified {@code MemoryType} to this {@code MemoryCell} unless it already contains the specified {@code MemoryType}
	//  *
	//  * @param type - a {@code MemoryType}
	//  * @return {@code True} if the specified {@code MemoryType} was added to this {@code MemoryCell}
	//  */
	// public boolean add(MemoryType type) {
	// 	return this.types.add(type);
	// }

	// /**
	//  * Removes the specified {@code MemoryType} to this {@code MemoryCell} unless it doesn't contain the specified {@code MemoryType}
	//  *
	//  * @param type - a {@code MemoryType}
	//  * @return {@code True} if the specified {@code MemoryType} was removed from this {@code MemoryCell}
	//  */
	// public boolean remove(MemoryType type) {
	// 	return this.types.remove(type);
	// }

	/**
	 * Returns a copy of the {@code Vec2D} position of the {@code Tile} associated with this {@code MemoryCell}
	 *
	 * @return a copy of the {@code Vec2D} position of the {@code Tile} associated with this {@code MemoryCell}
	 */
	public Vec2D getTilePos() {
		// Defensive copying to ensure immutability
		Vec2D toReturn = new Vec2D(this.tilePos.getX(),this.tilePos.getY());
		return toReturn;
	}

	@Override
	public boolean equals(Object toCompare) {
		// if(toCompare == null)  {
		// 	return false;
		// }

		// if(!MemoryCell.class.isAssignableFrom(toCompare.getClass())) {
		// 	return false;
		// }

		// final MemoryCell other = (MemoryCell) toCompare;

		// if(this.tilePos.getX()  != other.getTilePos().getX() && this.tilePos.getY() != other.getTilePos.getY()) {
		// 	return false;
		// }

		// return true;

		return this.hashCode() == toCompare.hashCode();
	}

	@Override
	public int hashCode() {
		// Because this Object is immutable, we can cache the hashCode.
		if (this.hashCode == 0){
			int hash = 3;

			// Types are deliberately left out of the hash function.
			// for(MemoryType MT : types) {
			// 	hash = 53 * hash + MT.hashCode();
			// }

			hash = 53 * hash + Double.hashCode(this.tilePos.getX());
			hash = 53 * hash + Double.hashCode(this.tilePos.getY());

			this.hashCode = hash;
		}

		return this.hashCode;
	}

}