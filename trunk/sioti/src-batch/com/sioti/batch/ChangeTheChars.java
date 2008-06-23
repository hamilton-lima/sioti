package com.sioti.batch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChangeTheChars {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(
				"docs/mapa.txt"));
		BufferedWriter writer = new BufferedWriter(new FileWriter(
				"docs/mapa-changed.txt"));

		String line = reader.readLine();
		while (line != null) {
			StringBuffer newLine = new StringBuffer();

			char[] lineAsChar = line.toCharArray();
			for (int i = 0; i < lineAsChar.length; i++) {
				newLine.append(changeChar(lineAsChar[i]));
			}

			writer.write(newLine.toString());
			writer.write(Character.LINE_SEPARATOR);
			line = reader.readLine();
		}

		writer.flush();
		writer.close();

	}

	private static char changeChar(char c) {

		if( c == 'C') return 'B';
		if( c == 'D') return 'B';
		if( c == 'E') return 'B';
		if( c == 'F') return 'B';
		
		if( c == 'H') return 'B';
		if( c == 'I') return 'J';
		if( c == 'M') return 'B';
		if( c == 'O') return 'B';
		return c;
		
	}

}
