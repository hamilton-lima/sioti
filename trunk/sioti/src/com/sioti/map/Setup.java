package com.sioti.map;

import java.awt.Point;

public class Setup {

	private static Setup instance;

	public static Setup getInstance() {
		if (instance == null) {
			instance = new Setup();
		}
		return instance;
	}

	// empty constructor
	private Setup() {

	}

	private int chunk_width = 16;

	private int chunk_height = 12;

	private Point startPointA = new Point(8096,744);

	private Point startPointB = new Point(8096,744);

	private String layer1Service = "http://localhost/sioti/layer1_around.php";

	/**
	 * return the value of chunk_height
	 * 
	 * @return the chunk_height
	 */
	public int getChunk_height() {
		return chunk_height;
	}

	/**
	 * updates chunk_height
	 * 
	 * @param chunk_height
	 *            the chunk_height to set
	 */
	public void setChunk_height(int chunk_height) {
		this.chunk_height = chunk_height;
	}

	/**
	 * return the value of chunk_width
	 * 
	 * @return the chunk_width
	 */
	public int getChunk_width() {
		return chunk_width;
	}

	/**
	 * updates chunk_width
	 * 
	 * @param chunk_width
	 *            the chunk_width to set
	 */
	public void setChunk_width(int chunk_width) {
		this.chunk_width = chunk_width;
	}

	/**
	 * return the value of startPointA
	 * 
	 * @return the startPointA
	 */
	public Point getStartPointA() {
		return startPointA;
	}

	/**
	 * updates startPointA
	 * 
	 * @param startPointA
	 *            the startPointA to set
	 */
	public void setStartPointA(Point startPointA) {
		this.startPointA = startPointA;
	}

	/**
	 * return the value of startPointB
	 * 
	 * @return the startPointB
	 */
	public Point getStartPointB() {
		return startPointB;
	}

	/**
	 * updates startPointB
	 * 
	 * @param startPointB
	 *            the startPointB to set
	 */
	public void setStartPointB(Point startPointB) {
		this.startPointB = startPointB;
	}

	public String getLayer1Service() {
		// TODO Auto-generated method stub
		return layer1Service;
	}

}
