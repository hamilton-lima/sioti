package com.sioti.util;

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
	 * @param message debug information
	 */
	public static void debug(String message) {
		if (DEBUG) {
			System.out.println(message);
		}

	}

	public static void warning(String message) {
		System.out.println(message);
	}

}
