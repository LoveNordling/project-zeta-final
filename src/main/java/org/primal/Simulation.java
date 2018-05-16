package org.primal;

import org.primal.map.Map;
import org.primal.map.Chunk;
import org.primal.util.ThrowingTask;
import org.primal.simulation.Simulatable;

import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A simulation running on multiple concurrent threads utilizing a {@link ScheduledThreadPoolExecutor} and
 * a {@link CyclicBarrier} for internal synchronization of Threads.
 *
 * <p><b> NOTE: </b> Since this simulation uses a {@link CyclicBarrier} to synchronize its Threads
 * scheduling more Objects than Threads can lead to Objects becoming de-synchronized from each other.
 *
 * <p>This simulation operates on 'cycles', a cycle starts when the first scheduled Object
 * starts its {@code simulate()} function  and ends when all Objects have finished their
 * respective function.
 *
 * <p>The simulation simulates Objects by calling their {@code simulate()} function and
 * supports any type of Object that implements {@link Simulatable}.
 *
 * <p>By default the simulation schedules at a rate of 16 {@code simulate()}s per millisecond.
 *
 *
 * @see java.util.concurrent.ScheduledThreadPoolExecutor
 * @see java.util.concurrent.CyclicBarrier
 * @see org.primal.simulation.Simulate
 */

public class Simulation {

    // The barrier used to synchronize the worker threads.
    private final CyclicBarrier updateLoopSynchronizationBarrier;

    // The threadpool used to manage worker threads.
    private ScheduledThreadPoolExecutor simulationExecutor;

    // The rate at which this simulation strives to run.
    private long rate = 16l;

    // TimeUnit used for scheduling.
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    // Holds all the currently running tasks.
    private ArrayList<ScheduledFuture> taskList;

    // This holds our Worker objects so we don't need to recreate them if we have to reschedule the simulation.
    private ArrayList<ThrowingTask> workerCache;

    private boolean running = false;

    /**
     * Creates a new Simulation with {@code corePoolSize} Threads.
     *
     * @param corePoolSize - The number of Threads this Simulation should use.
     */
    public Simulation(int corePoolSize) {
        this.simulationExecutor = new ScheduledThreadPoolExecutor(corePoolSize);
        updateLoopSynchronizationBarrier = new CyclicBarrier(corePoolSize);

        taskList = new ArrayList<ScheduledFuture>();
        workerCache = new ArrayList<ThrowingTask>();
    }

    /**
     * Creates a new Simulation with {@code corePoolSize} Threads.
     *
     * @param corePoolSize - The number of Threads this Simulation should use.
     * @param action       - A {@link Runnable} that will be executed after each cycle finishes but before the
     *        next cycle starts.
     */
    public Simulation(int corePoolSize, Runnable action) {
        this.simulationExecutor = new ScheduledThreadPoolExecutor(corePoolSize);
        updateLoopSynchronizationBarrier = new CyclicBarrier(corePoolSize, action);

        taskList = new ArrayList<ScheduledFuture>();
        workerCache = new ArrayList<ThrowingTask>();
    }

    /**
     * Creates a new Simulation with {@code corePoolSize} Threads.
     * <pre> Equivalent to {@code 
     *           Simulation simulation = Simulation(corePoolSize);
     *           simulation.schedule(tasks);}
     * </pre>
     *
     * @param corePoolSize - The number of Threads this Simulation should use.
     * @param tasks        - A Collection of tasks to be scheduled to this Simulation.
     */
    public Simulation(int corePoolSize, Collection<? extends Simulatable> tasks) {
        this(corePoolSize);

        this.schedule(tasks);
    }

    /**
     * Creates a new Simulation with {@code corePoolSize} Threads.
     * <pre> Equivalent to {@code 
     *           Simulation simulation = Simulation(corePoolSize);
     *           simulation.schedule(tasks);
     *           simulation.changeRate(rate,timeUnit);}
     * </pre>
     *
     * @param corePoolSize - The number of Threads this Simulation should use.
     * @param tasks        - A Collection of tasks to be scheduled to this Simulation.
     * @param action       - A {@link Runnable}that will be executed after each cycle finishes but before the
     *        next cycle starts.
     * @param rate         - The rate that this Simulation should run at.
     * @param timeUnit     - The {@link TimeUnit} this Simulation should run at.
     */
    public Simulation(int corePoolSize,Collection<? extends Simulatable> tasks, Runnable action, long rate, TimeUnit timeUnit) {
        this(corePoolSize, action);

        this.schedule(tasks);
        this.changeRate(rate, timeUnit);

    }

    /**
     * schedules all objects in {@code tasks} to be run in the simulation.
     *
     * @param tasks - A {@code Collection} of objects to simulate.
     */
    public void schedule(Collection<? extends Simulatable> tasks) {
        scheduleHelper(tasks);
    }

    private <T extends Simulatable> void scheduleHelper(Collection<T> tasks) {
        for (T task : tasks) {
            workerCache.add(new ThrowingTask(new Worker(task)));
        }
    }

    /**
     * Changes the rate at which this simulation runs.
     *
     * @param rate - The rate at which this simulation should run.
     * @param timeUnit - the {@link TimeUnit} this simulation should run.
     */
    public void changeRate(long rate, TimeUnit timeUnit) {
        this.rate = rate;
        this.timeUnit = timeUnit;
    }

    public void restart() {
        this.stop(false);
        this.start();
    }

    /**
     * Initializes a simulation with a given map.
     *
     * @param map the map the simulation is simulating.
     * @see org.primal.map.Map
     */
    public Simulation(Map map) {
        this.map = map;

        int threadNumber = Math.min(this.map.width * this.map.width, Runtime.getRuntime().availableProcessors());

        this.simulationExecutor = new ScheduledThreadPoolExecutor(threadNumber);

        updateLoopSynchronizationBarrier = new CyclicBarrier(threadNumber);

        this.rate = 16;
        this.timeUnit = TimeUnit.MILLISECONDS;

        taskList = new ArrayList<ScheduledFuture>();
        workerCache = new ArrayList<ThrowingTask>();

        // preStart();
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

        this.simulationExecutor = new ScheduledThreadPoolExecutor(threadNumber);

        updateLoopSynchronizationBarrier = new CyclicBarrier(threadNumber, action);

        this.rate = 16;
        this.timeUnit = TimeUnit.MILLISECONDS;

        taskList = new ArrayList<ScheduledFuture>();
        workerCache = new ArrayList<ThrowingTask>();

        // preStart();
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
            taskList.add(simulationExecutor.scheduleAtFixedRate(worker, 0, rate, timeUnit));
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
    // private void preStart() {

    //     for (Chunk[] chunks : this.map.getChunks()) {
    //         for (Chunk c : chunks) {
    //             workerCache.add(new ThrowingTask(new Worker(c)));
    //         }
    //     }
    // }

    public void execute(Runnable command) {
        simulationExecutor.execute(command);
    }

    public Future<?> submit(Runnable task) {
        return simulationExecutor.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return simulationExecutor.submit(task, result);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return simulationExecutor.submit(task);
    }






    /**
     * Private class implementing a wrapper around a given chunk to facilitate scheduling in a ThreadPool without forcing the Chunk to implement
     * the Runnable interface.
     * The Worker uses a CyclicBarrier to syncronize the different Worker threads in the simulation.
     *
     * @see java.util.concurrent.CyclicBarrier
     * @see org.primal.map.Chunk
     */
    private class Worker<T extends Simulatable> implements Runnable {

        private T myObject;
        // private boolean init = true;

        Worker(T object) {
            myObject = object;
        }

        @Override
        public void run() {
            // if (init) {
            //     myChunk.renderImage();
            //     init = false;
            // } else {
            //     myChunk.updateChunk();
            // }

            myObject.simulate();

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
