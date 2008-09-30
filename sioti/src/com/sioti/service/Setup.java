package com.sioti.service;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import pulpcore.math.Tuple2i;

public class Setup {

	private static Setup instance;

	private Map data;

	public static Setup getInstance() {
		if (instance == null) {
			instance = new Setup();
		}
		return instance;
	}

	// only used from classes of the same package
	void init(String text) {

		data = new HashMap();
		StringTokenizer tokenizer = new StringTokenizer(text, ";");

		while (tokenizer.hasMoreTokens()) {

			StringTokenizer one = new StringTokenizer(tokenizer.nextToken(),
					"=");

			if (one.countTokens() == 2) {
				String key = one.nextToken();
				String value = one.nextToken();
				data.put(key, value);
			}
		}

		this.chunkWidth = Integer.parseInt(data.get(CHUNK_WIDTH).toString());
		this.chunkHeight = Integer.parseInt(data.get(CHUNK_HEIGHT).toString());
		this.maxChunkX = Integer.parseInt(data.get(MAX_CHUNK_X).toString());
		this.maxChunkY = Integer.parseInt(data.get(MAX_CHUNK_Y).toString());

		StringTokenizer tokenizerStartPoint = new StringTokenizer(data.get(
				START_POINT_A).toString(), ",");

		if (tokenizerStartPoint.countTokens() == 2) {
			String startX = tokenizerStartPoint.nextToken();
			String startY = tokenizerStartPoint.nextToken();

			this.startPointA = new Tuple2i(Integer.parseInt(startX), Integer
					.parseInt(startY));
		}

		tokenizerStartPoint = new StringTokenizer(data.get(
				START_POINT_B).toString(), ",");

		if (tokenizerStartPoint.countTokens() == 2) {
			String startX = tokenizerStartPoint.nextToken();
			String startY = tokenizerStartPoint.nextToken();

			this.startPointB = new Tuple2i(Integer.parseInt(startX), Integer
					.parseInt(startY));
		}

	}

	private static final String CHUNK_WIDTH = "chunk_width";

	private static final String CHUNK_HEIGHT = "chunk_height";

	private static final String MAX_CHUNK_X = "max_chunk_x";

	private static final String MAX_CHUNK_Y = "max_chunk_y";

	private static final String START_POINT_A = "start_point_a";

	private static final String START_POINT_B = "start_point_b";

	private int chunkWidth = 12;

	private int chunkHeight = 12;

	private Tuple2i startPointA;

	private Tuple2i startPointB;

	private int maxChunkX;

	private int maxChunkY;

	public int getChunkWidth() {
		return chunkWidth;
	}

	public int getChunkHeight() {
		return chunkHeight;
	}

	public Tuple2i getStartPointA() {
		return startPointA;
	}

	public Tuple2i getStartPointB() {
		return startPointB;
	}

	public int getMaxChunkX() {
		return maxChunkX;
	}

	public int getMaxChunkY() {
		return maxChunkY;
	}
}
