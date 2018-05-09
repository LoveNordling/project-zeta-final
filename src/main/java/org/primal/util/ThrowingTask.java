package org.primal.util;

/**
 * This class is a wrapper used in {@code Simulation.java} to enable us to get Exceptions from Worker threads.
 * ScheduledThreadPool swallows all Exceptions by default, so this workaround is needed.
 *
 * @see org.primal.Simulation
 */
public class ThrowingTask implements Runnable {

    Runnable run;

    public ThrowingTask(Runnable run) {
        this.run = run;
    }

    @Override
    public void run() {
        try {
            run.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}