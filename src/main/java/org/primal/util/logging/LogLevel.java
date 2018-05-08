package org.primal.util.logging;

public enum LogLevel {

	ALL(7),
	PERF(6) {
		@Override
		public boolean isPerf() {
			return true;
		}
	},
	FINEST(5) {},
	FINE(4) {},
	INFO(3) {},
	WARN(2) {},
	ERR(1) {};

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