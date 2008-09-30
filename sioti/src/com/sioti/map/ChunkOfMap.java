package com.sioti.map;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.sioti.util.Log;

/**
 * Representation of a piece of the map
 * 
 * @author athanazio
 * 
 */
public class ChunkOfMap {

	private int x;

	private int y;

	private String[] data;

	public ChunkOfMap(String line, int columns) throws Exception {

		Map map = new HashMap();
		StringTokenizer tokenizer = new StringTokenizer(line, ";");
		int count = 1;
		while (tokenizer.hasMoreTokens()) {
			String one = tokenizer.nextToken();
			Log.debug("part length : " + one.length());

			PartOfChunk part = processOne(one, columns);
			map.put(part.getPosition(), part);
			count++;
			if (count > 9) {
				break;
			}
		}

		mixParts(map);
	}

	private void mixParts(Map map) {
		PartOfChunk nw = (PartOfChunk) map.get("NW");

		this.x = nw.getX();
		this.y = nw.getY();
		this.data = new String[nw.getData().length * 3];

		int skip = nw.getData().length;
		int height = 0;
		int max = skip;
		int inner = 0;

		for (int i = height; i < max; i++) {
			this.data[i] = nw.getData()[inner++];
		}

		PartOfChunk north = (PartOfChunk) map.get("N");
		inner = 0;
		for (int i = height; i < max; i++) {
			this.data[i] = this.data[i] + north.getData()[inner ++];
		}

		PartOfChunk ne = (PartOfChunk) map.get("NE");
		inner = 0;
		for (int i = height; i < max; i++) {
			this.data[i] = this.data[i] + ne.getData()[inner++];
		}

		height = skip;
		max = skip * 2;

		PartOfChunk west = (PartOfChunk) map.get("W");
		inner = 0;
		for (int i = height; i < max; i++) {
			this.data[i] = west.getData()[inner++];
		}

		PartOfChunk center = (PartOfChunk) map.get("C");
		inner = 0;
		for (int i = height; i < max; i++) {
			this.data[i] = this.data[i] + center.getData()[inner++];
		}

		PartOfChunk east = (PartOfChunk) map.get("E");
		inner = 0;
		for (int i = height; i < max; i++) {
			this.data[i] = this.data[i] + east.getData()[inner++];
		}

		height = skip * 2;
		max = skip * 3;

		PartOfChunk sw = (PartOfChunk) map.get("SW");
		inner = 0;
		for (int i = height; i < max; i++) {
			this.data[i] = sw.getData()[inner++];
		}

		PartOfChunk south = (PartOfChunk) map.get("S");
		inner = 0;
		for (int i = height; i < max; i++) {
			this.data[i] = this.data[i] + south.getData()[inner++];
		}

		PartOfChunk se = (PartOfChunk) map.get("SE");
		inner = 0;
		for (int i = height; i < max; i++) {
			this.data[i] = this.data[i] + se.getData()[inner++];
		}

	}

	private PartOfChunk processOne(String line, int columns) throws Exception {

		StringTokenizer tokenizer = new StringTokenizer(line, ",");
		PartOfChunk result = new PartOfChunk();

		if (tokenizer.countTokens() == 4) {
			result.setPosition(tokenizer.nextToken().trim());
			result.setX(Integer.parseInt(tokenizer.nextToken()));
			result.setY(Integer.parseInt(tokenizer.nextToken()));
			String mapData = tokenizer.nextToken().trim();

			if( Log.DEBUG ){
				Log.debug("position : " + result.getPosition() + ", data size : "
						+ mapData.length());
			}
			
			int rows = line.length() / columns;
			if (rows * columns < line.length()) {
				rows++;
			}
			result.setData(new String[rows]);

			for (int i = 0; i < result.getData().length; i++) {
				if (mapData.length() > columns) {
					result.getData()[i] = mapData.substring(0, columns);
					mapData = mapData.substring(columns);
				} else {
					result.getData()[i] = mapData;
				}
			}
		} else {
			throw new Exception("invalid data format");
		}

		return result;
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

}
