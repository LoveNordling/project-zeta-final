package org.primal;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.primal.entity.LivingEntity;
import org.primal.map.Chunk;
import org.primal.map.Map;

public class Simulation {

	private Map map;
	private ScheduledExecutorService simulationThreadPool;

	private boolean started,
					running;
	
	// Constructor
	public Simulation(Map map) {
		this.map = map;

		//8 is a temporary number
		this.simulationThreadPool = Executors.newScheduledThreadPool(8);

	}

	public void start() {
		for (Chunk c : this.map.getChunks()) {
			// 16 Milliseconds is approximatly 1/60 sec
		    simulationThreadPool.scheduleAtFixedRate(c, 0, 1, TimeUnit.SECONDS);
		}
	}
	
}