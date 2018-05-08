package org.primal.util.logging;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.primal.util.logging.LogLevel;

import java.io.PrintStream;


class EventLoggingTest {

	/*
	 * Deliberately not performing tests that create files to avoid clutter
	 */
	private EventLogger log;

	@Test
	init() {
		log = new EventLogger(LogLevel.ALL, 0);

		assertNotNull(log);
	}

	@Test
	easyWriteTest() {
		log = new EventLogger(LogLevel.FINEST, 0);

		log.logString(LogLevel.FINEST, "TestString");

		assertEquals("TestString",log.getString());
	}

	@Test
	filterTest() {
		log = new EventLogger(LogLevel.ERR, 0);

		log.logString(LogLevel.FINE, "TestString2");

		assertThat("TestString2",isNot(IsEqual(log.getString())));
	}

}