package org.primal;

import org.primal.map.Chunk;
import org.primal.map.Map;

import java.util.concurrent.*;

public class Simulation {

    private Map map;
    private ScheduledExecutorService simulationThreadPool;
    private boolean started, running;
    private final CyclicBarrier updateLoopSyncronizationBarrier;

    class Worker implements Runnable {
        Chunk myChunk;

        Worker(Chunk chunk) {
            myChunk = chunk;
        }

        public void run() {
            myChunk.updateChunk();

            try {
                updateLoopSyncronizationBarrier.await();

                // This error thrown if the thread was interrupted during execution
            } catch (InterruptedException ex) {
                return;

                /**
                 * This error thrown if ANOTHER thread was interrupted or the barrier was somehow broken
                 * Either by the barrier being reset(), or the barrier being broken when await() was called,
                 * or the barrier action failed due to an exception
                 */
            } catch (BrokenBarrierException ex) {
                return;
            }
        }
    }

    public Simulation(Map map) {
        this.map = map;

        // One thread for every chunk for now
        int chunkNumber = this.map.getChunks().size();

        //8 is a temporary number
        this.simulationThreadPool = Executors.newScheduledThreadPool(chunkNumber);

        Runnable syncronizationAction = () -> {
            for (Chunk chunk : this.map.getChunks()) {
            }
        };

        updateLoopSyncronizationBarrier = new CyclicBarrier(chunkNumber, syncronizationAction);

    }

    public void start() {
        for (Chunk c : this.map.getChunks()) {
            // 16 Milliseconds is approximatly 1/60 sec
            simulationThreadPool.scheduleAtFixedRate(new Worker(c), 0, 16, TimeUnit.MILLISECONDS);
        }

        this.started = true;

        updateLoop();
    }

    private void updateLoop() {

    }

}