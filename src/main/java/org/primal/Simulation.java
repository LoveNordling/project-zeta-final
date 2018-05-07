package org.primal;

import org.primal.map.Chunk;
import org.primal.util.ThrowingTask;
import org.primal.map.Map;
import org.primal.util.ThrowingTask;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Simulation {

    private final CyclicBarrier updateLoopSynchronizationBarrier;
    private Map map;
    private ScheduledExecutorService simulationThreadPool;
    private boolean started, running;

    public Simulation(Map map) {
        this.map = map;

        // One thread for every chunk for now
        int chunkNumber = this.map.width;

        //8 is a temporary number
        this.simulationThreadPool = Executors.newScheduledThreadPool(chunkNumber);

        Runnable synchronizationAction = () -> {
            for (Chunk[] chunks : this.map.getChunks()) {
                for (Chunk chunk : chunks) {
                }
            }
        };

        updateLoopSynchronizationBarrier = new CyclicBarrier(chunkNumber, synchronizationAction);

    }

    public void start() {

        for (Chunk[] chunks : this.map.getChunks()) {
            for (Chunk c : chunks) {
                // 16 Milliseconds is approximatly 1/60 sec
                simulationThreadPool.scheduleAtFixedRate(new ThrowingTask(new Worker(c)), 0, 16, TimeUnit.MILLISECONDS);
            }
        }

        this.started = true;

        updateLoop();
    }

    private void updateLoop() {

    }

    class Worker implements Runnable {

        Chunk myChunk;

        Worker(Chunk chunk) {
            myChunk = chunk;
        }

        @Override
        public void run() {
            myChunk.updateChunk();
            try {
                updateLoopSynchronizationBarrier.await();

                // This error thrown if the thread was interrupted during execution
            } catch (InterruptedException ex) {
                System.err.println("Thread " + Thread.currentThread() + "was interrupted");
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

}
