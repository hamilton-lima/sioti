package com.sioti.batch;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class AddMapToGZipFiles {

	// static final int lines = 60;
	// static final int columns = 80;

	static final int lines = 300;

	static final int columns = 400;

	static Connection conn;

	/**
	 * @param args
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws IOException, SQLException,
			InstantiationException, IllegalAccessException {
		BufferedReader reader = new BufferedReader(new FileReader(
				"docs/mapa-changedx5.txt"));

		int x = 0;
		int y = 0;
		ArrayList buffer = new ArrayList();

		String line = reader.readLine();
		while (line != null) {

			// read the lines
			StringBuffer[] listOfLines = new StringBuffer[lines];
			for (int i = 0; i < lines; i++) {
				StringBuffer one = new StringBuffer();
				if (line != null) {
					one.append(line);
				}
				listOfLines[i] = one;
				line = reader.readLine();
			}

			// cut the lines in columns
			while (listOfLines[0].length() >= columns) {
				StringBuffer one = new StringBuffer();
				for (int i = 0; i < listOfLines.length; i++) {
					// avoid empty lines
					if ((listOfLines[i].length() >= columns)) {
						one.append(listOfLines[i].substring(0, columns));
						listOfLines[i].delete(0, columns);
					} else {

						if (listOfLines[i].length() > 0) {
							listOfLines[i].delete(0,
									listOfLines[i].length() - 1);

						}
					}
				}
				// just the full cells, drop the border of the map (if exists)
				if (one.length() >= (lines * columns)) {
					System.out.println(x + "," + y);
					Chunk one1 = new Chunk(x, y, one.toString());
					buffer.add(one1);
					x = x + columns;
				}
			}

			saveToZIP(y, buffer);
			buffer = new ArrayList();
			y = y + lines;
			x = 0;
		}

	}

	private static void saveToZIP(int y, ArrayList buffer) throws IOException {
		System.out.println("save " + y);

		String outFilename = "mapsx5/" + y + ".map";
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				outFilename));

		Iterator iterator = buffer.iterator();
		while (iterator.hasNext()) {
			Chunk one = (Chunk) iterator.next();

			// Add ZIP entry to output stream.
			out.putNextEntry(new ZipEntry(Integer.toString(one.x)));
			out.write(one.data.getBytes());

			// Complete the entry
			out.closeEntry();
		}
		// Complete the ZIP file
		out.close();
	}

	// private static void saveToZIP(int x, int y, String string)
	// throws IOException {
	//
	// System.out.println(x + "," + y);
	//
	// String outFilename = "maps/" + x + ".map";
	// ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
	// outFilename));
	//
	// // Add ZIP entry to output stream.
	// out.putNextEntry(new ZipEntry(Integer.toString(y)));
	// out.write(string.getBytes());
	//
	// // Complete the entry
	// out.closeEntry();
	// // Complete the ZIP file
	// out.close();
	// }
	//
	// private static void saveToGZIP(int x, int y, String string)
	// throws FileNotFoundException, IOException {
	//
	// // Create the GZIP output stream
	// String outFilename = "maps/" + x + "_" + y + ".map";
	// System.out.println(outFilename);
	//
	// GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(
	// outFilename));
	//
	// out.write(string.getBytes());
	// out.flush();
	// out.close();
	//
	// }

}
