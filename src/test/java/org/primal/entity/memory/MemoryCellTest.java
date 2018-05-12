package org.primal.entity.memory;

import org.primal.map.Map;
import org.primal.tile.Tile;
import org.primal.util.Vec2D;
import org.primal.entity.memory.MemoryType;
import org.primal.entity.memory.MemoryCell;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;
import java.util.EnumSet;

public class MemoryCellTest {
	private Map map;
	private Tile tile;
	private Tile tile2;
	private Set<MemoryType> enumSet;


	@BeforeEach
	public void init() {
		map = new Map(1);
		tile = map.getTile(0,0);
		tile2 = map.getTile(1,11);
		enumSet = EnumSet.noneOf(MemoryType.class);
	}

	@AfterEach
	public void destroy() {
		map = null;
		tile = null;
		tile2 = null;
		enumSet = null;
	}

	@Test
	public void initTest() {
		MemoryCell MC = new MemoryCell(tile,enumSet);
		assertNotNull(MC);
	}

	@Test
	public void containsTest() {
		enumSet.add(MemoryType.FOOD);
		MemoryCell MC = new MemoryCell(tile,enumSet);

		assertTrue(MC.contains(MemoryType.FOOD));
	}

	@Test
	public void containsTestFalse() {
		enumSet.add(MemoryType.FOOD);
		MemoryCell MC = new MemoryCell(tile,enumSet);

		assertFalse(MC.contains(MemoryType.WATER));
	}

	@Test
	public void getTilePostTest() {
		MemoryCell MC = new MemoryCell(tile,enumSet);

		assertEquals(new Vec2D(0,0), MC.getTilePos());
	}

	@Test
	public void equalsTest() {
		MemoryCell MC = new MemoryCell(tile,enumSet);
		enumSet.add(MemoryType.FOOD);
		MemoryCell MC2 = new MemoryCell(tile,enumSet);

		assertTrue(MC.equals(MC2));
		assertTrue(MC2.equals(MC));
	}

	@Test
	public void equalsTestFalse() {
		MemoryCell MC = new MemoryCell(tile,enumSet);
		MemoryCell MC2 = new MemoryCell(tile2,enumSet);

		assertFalse(MC.equals(MC2));
		assertFalse(MC2.equals(MC));
	}

}