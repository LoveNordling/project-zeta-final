package org.primal.entity.memory;

import org.primal.map.Map;
import org.primal.tile.Tile;
import org.primal.util.Vec2D;
import org.primal.entity.memory.MemoryType;
import org.primal.entity.memory.MemoryCell;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.EnumSet;
import java.util.ArrayList;

public class MemoryCellTest {
	private Map map;
	private Tile tile;
	private Set<MemoryType> enumSet;


	@BeforeEach
	public void init() {
		map = new Map(1);
		tile = map.getTile(0,0);
		enumSet = EnumSet.noneOf(MemoryType.class);
	}

	@AfterEach
	public void destroy() {
		map = null;
		tile = null;
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
	public void containsTestFail() {
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
		MemoryCell MC2 = new MemoryCell(tile,enumSet);

		assertTrue(MC.equals(MC2));
		assertTrue(MC2.equals(MC));
	}

}