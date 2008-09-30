package com.sioti.util;

import java.net.MalformedURLException;

import pulpcore.CoreSystem;
import pulpcore.sprite.Label;

/**
 * logging layer
 * 
 * represents the type
 * 
 * @author HAL
 * 
 */
public class Log {

	public static final boolean DEBUG = true;

	/**
	 * show debug message
	 * 
	 * @param message
	 *            debug information
	 */
	public static void debug(String message) {
		if (DEBUG) {
			CoreSystem.print(message);
		}

	}

	public static void warning(String message) {
		CoreSystem.print(message);
	}

	public static void error(Exception e, String message) {
		CoreSystem.print(message, e);
	}
}
