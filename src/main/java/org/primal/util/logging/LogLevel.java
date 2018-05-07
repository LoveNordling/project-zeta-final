package org.primal.util.logging;

public enum LogLevel {

	PERF(0) {
		@Override
		public boolean isPerf() {
			return true;
		}
	},
	FINEST(1) {},
	FINE(2) {},
	INFO(3) {},
	WARN(4) {},
	ERR(5) {},
	ALL(6) {};

	private int level;

	private LogLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
	    return level;
	}

	public boolean isPerf() {
		return false;
	}
}