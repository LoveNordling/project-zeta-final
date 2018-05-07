package org.primal.util.logging;

import org.primal.util.logging.LogLevel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class EventLogger {

	private PrintWriter log;
	private LogLevel level;

	EventLogger(LogLevel level) {
		this.level = level;

		try {
		    @SuppressWarnings("resource")
		    log = new PrintWriter("output/event.log");
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
	}

	EventLogger(LogLevel level, String file){
		this.level = level;

		try {
		    @SuppressWarnings("resource")
		    log = new PrintWriter("output/" + file + ".log");
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
	}

	public synchronized void log(LogLevel level, String msg) {
		if(this.level.getLevel() > level.getLever() && this.level.isPerf() == level.isPerf()) {
			log.println(msg);
			log.flush();
		}
	}

	// Used for performance logging.
	public synchronized void log(LogLevel level, String msg, long time) {
		if(this.level.getLevel() > level.getLever() && this.level.isPerf() == level.isPerf()) {
			log.println(msg + " : " + time);
			log.flush();
		}
	}

	public LogLevel getLevel() {
	    return level;
	}
}