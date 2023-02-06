package com.example.springboot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppLogger.class);

	public static <T>void info(T logging) {
		LOGGER.info("\u001B[32m" + logging + "\u001B[0m");
	}

	public static <T>void error(T logging) {
		LOGGER.error("\u001B[31m" + logging + "\u001B[0m");
	}
}
