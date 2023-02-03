package com.example.springboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppLogger.class);

	public static void info(String logging) {
		LOGGER.info("\u001B[32m" + logging + "\u001B[0m");
	}

	public static void error(String logging) {
		LOGGER.error("\u001B[31m" + logging + "\u001B[0m");
	}
}
