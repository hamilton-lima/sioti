package com.sioti.batch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AddMapToDatabase {

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
				"docs/mapa-changedx5.txt"));

		prepareDatabase();

		int x = 0;
		int y = 0;

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
					saveToDatabase(x, y, one.toString());
					x = x + columns;
				}
			}
			y = y + lines;
			x = 0;
		}

	}

	private static void prepareDatabase() throws SQLException,
			InstantiationException, IllegalAccessException {
		DriverManager.registerDriver( new org.gjt.mm.mysql.Driver());
		conn = DriverManager.getConnection("jdbc:mysql://localhost/sioti",
				"root", "wrong password");
	}

	private static void saveToDatabase(int x, int y, String string)
			throws SQLException {
		String sql = "INSERT INTO LAYER1(x,y,data) values(" + x + "," + y
				+ ",'" + string + "')";

		System.out.println(x + "," + y);
		Statement state = conn.createStatement();
		state.executeUpdate(sql);
		state.close();

	}

}
