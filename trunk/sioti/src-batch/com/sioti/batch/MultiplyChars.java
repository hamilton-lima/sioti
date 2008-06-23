package com.sioti.batch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MultiplyChars {

	static final int n = 5;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(
				"docs/mapa-changedx5.txt"));
		BufferedWriter writer = new BufferedWriter(new FileWriter(
				"docs/mapa-changedx5x5.txt"));

		String line = reader.readLine();
		while (line != null) {
			StringBuffer newLine = new StringBuffer();

			char[] lineAsChar = line.toCharArray();
			for (int i = 0; i < lineAsChar.length; i++) {
				for (int j = 0; j < n; j++) {
					newLine.append(lineAsChar[i]);
				}
			}

			for (int j = 0; j < n; j++) {
				writer.write(newLine.toString());
				writer.write(Character.LINE_SEPARATOR);
			}
			line = reader.readLine();
		}

		writer.flush();
		writer.close();

	}

}
