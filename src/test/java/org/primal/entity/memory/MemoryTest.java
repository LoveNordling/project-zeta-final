package org.primal.entity.memory;

import org.primal.map.Map;
import org.primal.tile.Tile;
import org.primal.util.Vec2D;
import org.primal.entity.memory.MemoryType;
import org.primal.entity.memory.MemoryCell;
import org.primal.entity.memory.Memory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.EnumSet;
import java.util.ArrayList;

public class MemoryTest {
	private Map map;
	private Tile tile1;
	private Set<MemoryType> enumSet;
	private Memory memory;


	@BeforeEach
	public void init() {
		map = new Map(1);
		tile1 = map.getTile(0,0);
		enumSet = EnumSet.noneOf(MemoryType.class);
		memory = new Memory();
	}

	@AfterEach
	public void destroy() {
		map = null;
		tile1 = null;
		enumSet = null;
		memory = null;
	}

	@Test
	public void recallPositionEmptySetTest() {
		memory.remember(tile1,enumSet);
		assertNull(memory.recallPosition(MemoryType.FOOD));
	}

	@Test
	public void recallPositionTest() {
		enumSet.add(MemoryType.FOOD);
		memory.remember(tile1,enumSet);
		assertEquals(new Vec2D(0,0),memory.recallPosition(MemoryType.FOOD));
	}

	@Test
	public void recallPositionOverwriteTest() {
		memory.remember(tile1,enumSet);
		assertNull(memory.recallPosition(MemoryType.FOOD));
		enumSet.add(MemoryType.FOOD);
		memory.remember(tile1,enumSet);
		assertEquals(new Vec2D(0,0), memory.recallPosition(MemoryType.FOOD));
	}


}