package org.primal;

import org.primal.map.Chunk;
import org.primal.map.Map;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation {

    private Map map;
    private ScheduledExecutorService simulationThreadPool;
    private boolean started, running;

    public Simulation(Map map) {
        this.map = map;

        //8 is a temporary number
        this.simulationThreadPool = Executors.newScheduledThreadPool(8);

    }

    public void start() {
        for (Chunk c : this.map.getChunks()) {
            // 16 Milliseconds is approximatly 1/60 sec
            simulationThreadPool.scheduleAtFixedRate(c, 0, 16, TimeUnit.MILLISECONDS);
        }
    }

}