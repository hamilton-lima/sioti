package com.sioti.batch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AddMapToDatabaseScript {

	static final int lines = 60;

	static final int columns = 80;

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
				"docs/map/mapa-changedx5.txt"));

		StringBuffer buffer = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		
		BufferedWriter writer = null;
		int x = 0;
		int y = 0;
		int count = 1;
		int filecount = 0;
		
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
					if (x > 0) {
						buffer.append(",");
					}
					buffer.append("(" + x + "," + y + ",'" + one.toString()
							+ "') \n");
					x = x + columns;
				}
			}

			sql.append( "INSERT INTO sioti_layer1(x,y,data) VALUES \n"
					+ buffer.toString() + "; \n");
			buffer = new StringBuffer();

			if( count ++ >= 20 ){
				filecount ++;
				writer = new BufferedWriter(new FileWriter(
						"docs/map/mapa-changedx5-sql-" + filecount +".sql"));
				writer.write(sql.toString());
				writer.flush();
				writer.close();

				sql = new StringBuffer();
				count = 0;
			}

			y = y + lines;
			x = 0;
			System.out.println("x:" + x + " y:" + y);

		}

		filecount ++;
		writer = new BufferedWriter(new FileWriter(
				"docs/map/mapa-changedx5-sql-" + filecount +".sql"));
		writer.write(sql.toString());
		writer.flush();
		writer.close();
		
	}

}
