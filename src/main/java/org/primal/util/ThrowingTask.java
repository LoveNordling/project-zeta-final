package org.primal.util;

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
			System.out.println("Exception occured");
			ex.printStackTrace();
		}
	}

}