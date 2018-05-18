package org.primal;

import org.primal.map.Map;
import org.primal.map.Chunk;
import org.primal.util.ThrowingTask;
import org.primal.simulation.Simulatable;


import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import java.util.concurrent.atomic.AtomicInteger;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A simulation running on multiple concurrent threads utilizing a {@link ThreadPoolExecutor}.
 *
 * <p>This simulation operates on 'cycles', a cycle starts when the first scheduled Object
 * starts its {@code simulate()} function  and ends when all Objects have finished their
 * respective function and any post-cycle actions have been finished.
 *
 * <p>The simulation simulates Objects by calling their {@code simulate()} function and
 * supports any type of Object that implements {@link Simulatable}.
 *
 * <p>By default the simulation schedules at a rate of 16 {@code simulate()}s per millisecond
 * note that this is a lower bound rate, heavy cycles might take longer to complete thereby
 * causing subsequent cycles to be delayed.
 *
 * <p> This simulation only guarantees that cycles will be scheduled no faster than the rate.
 *
 *
 * @see java.util.concurrent.ThreadPoolExecutor
 * @see org.primal.simulation.Simulatable
 */

public class Simulation {

    // The threadpool used to manage worker threads.
    private ExecutorService simulationExecutor;

    // Timer Thread used for rate limiting.
    private ScheduledExecutorService timer;

    // We store the timer Future so we can cancel it if needed.
    private ScheduledFuture timerTask;

    // The rate at which this simulation strives to run.
    private long rate = 16l;

    // TimeUnit used for scheduling.
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    // The total number of objects to be simulated, stored for comparison.
    private AtomicInteger objectCounter;

    // The total number of times objects have been simulated, should be comparable to objectCounter * cycleCounter.
    private AtomicInteger workCompletedCounter;

    // The number of cycles this simulation has completed.
    private AtomicInteger cycleCounter = new AtomicInteger(0);

    // Holds all the currently running tasks.
    private ArrayList<Future<?>> taskList;

    // This holds our Worker objects so we don't need to recreate them if we have to reschedule the simulation.
    private ArrayList<Worker<? extends Simulatable>> workerCache;

    private Runnable postUpdateAction;

    private boolean running = false;

    /**
     * Creates a new Simulation with {@code corePoolSize} Threads.
     *
     * @param corePoolSize - The number of Threads this Simulation should use.
     */
    public Simulation(int corePoolSize) {
        this.simulationExecutor = Executors.newFixedThreadPool(corePoolSize);
        this.timer = Executors.newScheduledThreadPool(1);
        //updateLoopSynchronizationBarrier = new CyclicBarrier(corePoolSize);

        postUpdateAction = () -> {
            this.postUpdateActions();
        };

        taskList = new ArrayList<Future<?>>();
        workerCache = new ArrayList<Worker<? extends Simulatable>>();
        objectCounter = new AtomicInteger(0);
        workCompletedCounter = new AtomicInteger(0);

    }

    /**
     * Creates a new Simulation with {@code corePoolSize} Threads.
     *
     * @param corePoolSize - The number of Threads this Simulation should use.
     * @param action       - A {@link Runnable} that will be executed after each cycle finishes but before the
     *        next cycle starts.
     */
    public Simulation(int corePoolSize, Runnable action) {
        this.simulationExecutor = Executors.newFixedThreadPool(corePoolSize);
        this.timer = Executors.newScheduledThreadPool(1);
        //updateLoopSynchronizationBarrier = new CyclicBarrier(corePoolSize, action);

        postUpdateAction = () -> {
            action.run();
            this.postUpdateActions();
        };

        taskList = new ArrayList<Future<?>>();
        workerCache = new ArrayList<Worker<? extends Simulatable>>();
        objectCounter = new AtomicInteger(0);
        workCompletedCounter = new AtomicInteger(0);

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
     * This is everything we need to do after each cycle
     */
    private void postUpdateActions() {
        System.out.println(workCompletedCounter.get());
        if(workCompletedCounter.compareAndSet(objectCounter.getAcquire(),0)) {
        } else {
            System.err.println("Error in Simulation::postUpdateActions()! workCompletedCounter had faulty value");
        }
    }

    private void updateLoop() {
        executeObjects();
        try{
            final Runnable command = this.postUpdateAction;
            command.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        cycleCounter.incrementAndGet();
        System.out.println(cycleCounter.get());

        if(cycleCounter.get() >= 10) {
            while(!timerTask.cancel(false));
            System.out.println("Exited");
        }

    }

    private void executeObjects() {
        try {
            taskList = new ArrayList<Future<?>>(simulationExecutor.invokeAll(workerCache));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * schedules all objects in {@code tasks} to be run in the simulation.
     *
     * @param tasks - A {@code Collection} of objects to simulate.
     */
    public void schedule(Collection<? extends Simulatable> tasks) {
        scheduleHelper(tasks);
        System.out.println(objectCounter.get());
    }

    private <T extends Simulatable> void scheduleHelper(Collection<T> tasks) {
        for (T task : tasks) {
            workerCache.add(new Worker<T>(task));
            objectCounter.incrementAndGet();
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
     * Starts the simulation by scheduling all the chunks in {@code map} to the threadpool.
     * It does this by calling {@code map}s {@code getChunks()} method and iterating over the result to schedule the chunks.
     *
     * @see org.primal.map.Chunk
     * @see org.primal.map.Map#getChunks()
     * @see org.primal.util.ThrowingTask
     */
    public void start() {
        System.out.println("Starting simulation: " + rate + ":" + timeUnit);
        // taskList.clear();
        // for (ThrowingTask worker : this.workerCache) {
        //     taskList.add(simulationExecutor.scheduleAtFixedRate(worker, 0, rate, timeUnit));
        // }
        Runnable update = () -> {updateLoop();};
        System.out.println(workCompletedCounter.get());
        timerTask = timer.scheduleAtFixedRate(update,0,this.rate,this.timeUnit);
    }

    /**
     * Stops the {@code ScheduledFuture}s that represent the simulation.
     *
     * @param Force whether or not to force interruption of the threads.
     * @return Always returns {@code true}.
     */
    private boolean stop(boolean force) {
        // for (ScheduledFuture SF : taskList) {
        //     //cleanStop = (cleanStop || SF.cancel(force));
        //     while(!(SF.cancel(force)));
        // }
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
     * Private class implementing a wrapper around a given Object to facilitate scheduling in a ThreadPool without forcing the Object to implement
     * the Callable interface.
     * 
     */
    private class Worker<T extends Simulatable> implements Callable<Void> {

        private T myObject;
        // private boolean init = true;

        Worker(T object) {
            myObject = object;
        }

        @Override
        public Void call() {
            // if (init) {
            //     myChunk.renderImage();
            //     init = false;
            // } else {
            //     myChunk.updateChunk();
            // }
            try {
                myObject.simulate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            workCompletedCounter.incrementAndGet();

            return null;

        }
    }
}
