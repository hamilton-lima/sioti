package com.sioti.batch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WhatAreTheChars {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(
				"docs/mapa-changedx5.txt"));

		Map existingChars = new HashMap();

		String line = reader.readLine();
		while (line != null) {

			char[] lineAsChar = line.toCharArray();
			for (int i = 0; i < lineAsChar.length; i++) {
				NameAndValuePair pair = (NameAndValuePair) existingChars
						.get(Character.valueOf(lineAsChar[i]));

				if (pair == null) {
					pair = new NameAndValuePair();
					pair.c = Character.valueOf(lineAsChar[i]);
					pair.count = 1;
					existingChars.put(pair.c, pair);
				} else {
					pair.count++;
				}

			}
			line = reader.readLine();
		}

		Iterator iterator = existingChars.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			NameAndValuePair pair = (NameAndValuePair) existingChars.get(key);
			System.out.println(key + " " + pair.count);
		}

	}

	static class NameAndValuePair {

		Character c;

		int count;
	}

}
