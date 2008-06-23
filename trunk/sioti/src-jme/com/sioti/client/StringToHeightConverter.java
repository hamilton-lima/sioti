package com.sioti.client;

import java.util.HashMap;
import java.util.Map;

/**
 * define rules to convert from char to int 
 * to be used in the initial convertion from the maps
 * in the character format to the integer format
 * 
 * represents the type 
 * @author limajuni
 *
 */
public class StringToHeightConverter {

	private Map rules;

	public StringToHeightConverter() {
		rules = new HashMap();
	}

	public void addRule(char c, int i) {
		rules.put(new Character(c), new Integer(i));
	}

	public int convert(char c) {
		Object one = rules.get(new Character(c));
		if (one != null) {
			return ((Integer) one).intValue();
		}
		return 0;
	}
}
