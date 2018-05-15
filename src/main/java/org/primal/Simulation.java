package org.primal;

import org.primal.map.Chunk;
import org.primal.map.Map;
import org.primal.util.ThrowingTask;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A simulation running on multiple concurrent threads utilizing a scheduledExecutor.
 * The simulation also uses a CyclicBarrier for synchronization between worker threads.
 * The simulation currently strives to run at 10 cycle per second.
 * One 'cycle' is defined as the time it takes all the Chunks in a given map to complete their {@code updateChunks()} method.
 * Currently tightly coupled with the Primal project.
 *
 * @see java.util.concurrent.ScheduledExecutorService
 * @see java.util.concurrent.CyclicBarrier
 * @see org.primal.map.Chunk#updateChunk()
 */

public class Simulation {

    // The barrier used to synchronize the worker threads.
    private final CyclicBarrier updateLoopSynchronizationBarrier;

    // The map the simulation is running on.
    private Map map;

    // The threadpool used to manage worker threads.
    private ScheduledExecutorService simulationThreadPool;

    /**
     * Initializes a simulation with a given map.
     *
     * @param map the map the simulation is simulating.
     * @see org.primal.map.Map
     */
    public Simulation(Map map) {
        this.map = map;

        int threadNumber = Math.min(this.map.width * this.map.width, Runtime.getRuntime().availableProcessors());

        this.simulationThreadPool = Executors.newScheduledThreadPool(threadNumber);

        updateLoopSynchronizationBarrier = new CyclicBarrier(threadNumber);
    }

    /**
     * Initializes a simulation with a given map and a {@code Runnable} that will be called at the end of every cycle.
     *
     * @param map    the map the simulation is simulating.
     * @param action the {@code Runnable} to be executed at the end of each cycle.
     * @see org.primal.map.Map
     */
    public Simulation(Map map, Runnable action) {
        this.map = map;

        int threadNumber = Math.min(this.map.width * this.map.width, Runtime.getRuntime().availableProcessors());

        this.simulationThreadPool = Executors.newScheduledThreadPool(threadNumber);

        updateLoopSynchronizationBarrier = new CyclicBarrier(threadNumber, action);
    }

    /**
     * Starts the simulation by scheduling all the chunks in {@code map} to the threadpool.
     * It does this by calling {@code map}s {@code getChunks()} method and iterating over the result to schedule the chunks.
     *
     * @see org.primal.map.Chunk
     * @see org.primal.map.Map#getChunks()
     * @see org.primal.util.ThrowingTask
     */
    public void start() {
        for (Chunk[] chunks : this.map.getChunks()) {
            for (Chunk c : chunks) {
                /*
                 * ThrowingTask is a wrapper around Worker to circumvent the fact that ScheduledThreadPools swallows all exceptions by default.
                 * 16 Milliseconds is approximately 1/60 seconds.
                 */
                simulationThreadPool.scheduleAtFixedRate(new ThrowingTask(new Worker(c)), 0, 16, TimeUnit.MILLISECONDS);
            }
        }
    }

    /**
     * Private class implementing a wrapper around a given chunk to facilitate scheduling in a ThreadPool without forcing the Chunk to implement
     * the Runnable interface.
     * The Worker uses a CyclicBarrier to syncronize the different Worker threads in the simulation.
     *
     * @see java.util.concurrent.CyclicBarrier
     * @see org.primal.map.Chunk
     */
    private class Worker implements Runnable {

        Chunk myChunk;
        private boolean init = true;

        Worker(Chunk chunk) {
            myChunk = chunk;
        }

        @Override
        public void run() {
            if (init) {
                myChunk.renderImage();
                init = false;
            } else {
                myChunk.updateChunk();
            }

            try {
                updateLoopSynchronizationBarrier.await();

                // This error thrown if the thread was interrupted during execution
            } catch (InterruptedException ex) {
                System.err.println("Thread " + Thread.currentThread() + "was interrupted");

                /*
                 * This error thrown if ANOTHER thread was interrupted or the barrier was somehow broken
                 * Either by the barrier being reset(), or the barrier being broken when await() was called,
                 * or the barrier action failed due to an exception
                 */
            } catch (BrokenBarrierException ex) {
                System.err.println("Error: " + ex);
            }
        }
    }
}
