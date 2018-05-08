package org.primal.util.logging;

import org.primal.util.logging.LogLevel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.StringReader;



public class EventLogger {

	private StringWriter logString;
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

	// Dependency injection to support custom implementations
	EventLogger(LogLevel level, PrintStream file) {
		this.level = level;

		try {
		    @SuppressWarnings("resource")
		    log = new PrintWriter(file);
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
	}

	// Implemented to support logging directly to a StringWriter
	EventLogger(LogLevel level, int buffer) {
		if(buffer == 0){
			this.logString = new StringWriter();
		} else {
			this.logString = new StringWriter(buffer);
		}
	}

	public synchronized void logString(LogLevel level, String msg) {
		if(this.level.getLevel() <= level.getLevel() || this.level.getLevel() == LogLevel.ALL.getLevel()) {
			logString.write(msg);
			log.flush();
		}
	}

	public synchronized void log(LogLevel level, String msg) {
		if(this.level.getLevel() <= level.getLevel() || this.level.getLevel() == LogLevel.ALL.getLevel()) {
			log.println(msg);
			log.flush();
		}
	}

	// Used for performance logging.
	public synchronized void log(LogLevel level, String msg, long time) {
		if(this.level.getLevel() <= level.getLevel() || this.level.getLevel() == LogLevel.ALL.getLevel()) {
			log.println(msg + " : " + time);
			log.flush();
		}
	}

	public LogLevel getLevel() {
	    return level;
	}

	public String getString() {
		return logString.toString();
	}
}