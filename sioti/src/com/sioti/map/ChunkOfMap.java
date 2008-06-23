package com.sioti.map;

import java.util.StringTokenizer;

/**
 * Representation of a piece of the map
 * @author athanazio
 *
 */
public class ChunkOfMap {

	private String position;

	private int x;

	private int y;

	private String[] data;

	public ChunkOfMap(String line, int columns, int lines) {
		System.out.println(line);
		StringTokenizer tokenizer = new StringTokenizer(line, ",");
		this.position = tokenizer.nextToken();
		this.x = Integer.parseInt(tokenizer.nextToken());
		this.y = Integer.parseInt(tokenizer.nextToken());
		String mapData = tokenizer.nextToken();

		data = new String[columns];
		for (int i = 0; i < data.length; i++) {
			if (mapData.length() > columns) {
				data[i] = mapData.substring(0, columns);
				mapData = mapData.substring(columns);
			} else {
				data[i] = mapData;
			}
		}
	}

	/**
	 * return the value of data
	 * 
	 * @return the data
	 */
	public String[] getData() {
		return data;
	}

	/**
	 * return the value of x
	 * 
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * return the value of y
	 * 
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * return the value of position
	 * 
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

}
