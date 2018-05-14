package org.primal;

import org.primal.map.Chunk;
import org.primal.map.Map;
import org.primal.util.ThrowingTask;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;

import java.util.ArrayList;

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

    // The rate at which this simulation strives to run.
    private long rate;

    // TimeUnit used for scheduling.
    private TimeUnit timeUnit;

    // Holds all the currently running tasks.
    private ArrayList<ScheduledFuture> taskList;

    // This holds our Worker objects so we don't need to recreate them if we have to reschedule the simulation.
    private ArrayList<ThrowingTask> workerCache;

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

        this.rate = 16;
        this.timeUnit = TimeUnit.MILLISECONDS;

        taskList = new ArrayList<ScheduledFuture>();
        workerCache = new ArrayList<ThrowingTask>();

        preStart();
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

        this.rate = 16;
        this.timeUnit = TimeUnit.MILLISECONDS;

        taskList = new ArrayList<ScheduledFuture>();
        workerCache = new ArrayList<ThrowingTask>();

        preStart();
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
        System.out.println("Starting simulation: " + rate + ":" + timeUnit);
        taskList.clear();
        // for (Chunk[] chunks : this.map.getChunks()) {
        //     for (Chunk c : chunks) {
                
        //          * ThrowingTask is a wrapper around Worker to circumvent the fact that ScheduledThreadPools swallows all exceptions by default.
        //          * 16 Milliseconds is approximately 1/60 seconds.
                 
        //         taskList.add(simulationThreadPool.scheduleAtFixedRate(new ThrowingTask(new Worker(c)), 0, rate, timeUnit));
        //     }
        // }
        for (ThrowingTask worker : this.workerCache) {
            taskList.add(simulationThreadPool.scheduleAtFixedRate(worker, 0, rate, timeUnit));
        }
    }

    /**
     * Stops the {@code ScheduledFuture}s that represent the simulation.
     *
     * @param Force whether or not to force interruption of the threads.
     * @return Always returns {@code true}.
     */
    private boolean stop(boolean force) {
        for (ScheduledFuture SF : taskList) {
            //cleanStop = (cleanStop || SF.cancel(force));
            while(!(SF.cancel(force)));
        }
        return true;
    }

    /**
     * Stops the {@code ScheduledFuture}s that represent the simulation.
     *
     * @return Always returns {@code true}.
     */
    public boolean stop() {
        return this.stop(false);
    }

    /**
     * Stops the {@code ScheduledFuture}s that represent the simulation, forcing interruption of the threads if necessery.
     *
     * @return Always returns {@code true}.
     */
    public boolean stopForce() {
        return this.stop(true);
    }

    /**
     * Rechedules the simulation to run its updates at {@code rate} updates per {@code unit}.
     *
     * @param rate - The rate at which to update.
     * @param unit - The {@code TimeUnit} at which to update.
     * @return Always returns {@code true}.
     * @see java.util.concurrent.TimeUnit
     */
    public boolean reSchedule(long rate, TimeUnit unit) {
        this.stop();

        this.rate = rate;
        this.timeUnit = unit;
        this.start();

        return true;
    }

    /**
     * Shorthand for {@code reSchedule(rate, this.getTimeUnit())}.
     */
    public boolean reSchedule(long rate) {
        return this.reSchedule(rate,this.timeUnit);
    }

    /**
     * Returns the rate at which this simulation updates.
     *
     * @return The rate at which this simulation updates.
     */    
    public long getRate() {
        return this.rate;
    }

    /**
     * Returns the {@code TimeUnit} at which this simulation updates.
     *
     * @return The {@code TimeUnit} at which this simulation updates.
     */
    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

    /**
     * This performes some operations that only needs to be done once per simulation, before any other operations should begin.
     */
    private void preStart() {

        for (Chunk[] chunks : this.map.getChunks()) {
            for (Chunk c : chunks) {
                workerCache.add(new ThrowingTask(new Worker(c)));
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
                ex.printStackTrace();

                /*
                 * This error thrown if ANOTHER thread was interrupted or the barrier was somehow broken
                 * Either by the barrier being reset(), or the barrier being broken when await() was called,
                 * or the barrier action failed due to an exception
                 */
            } catch (BrokenBarrierException ex) {
                System.err.println("Error: " + ex);
                ex.printStackTrace();
            }
        }
    }
}
