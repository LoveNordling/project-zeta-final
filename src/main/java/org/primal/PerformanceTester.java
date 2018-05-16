package org.primal;

import org.primal.map.Map;
import org.primal.map.Chunk;

import java.util.concurrent.ThreadLocalRandom;

public class PerformanceTester {

	private Map map;

	public PerformanceTester() {

	}

	public void makeNewMap(int width) {
		this.map = new Map(width);
	}

	public void burnTestGetChunk() {
		double randX;
		double randY;
		for (int x = 0; x < 100000 ; x++ ) {
			randX = ThreadLocalRandom.current().nextDouble(0, (map.width * 16));
			randY = ThreadLocalRandom.current().nextDouble(0, (map.width * 16));

			map.getChunk(((int) randX) / 16,((int) randY) / 16);
		}
	}
}