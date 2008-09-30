package com.sioti;

import java.util.HashMap;
import java.util.Map;

import pulpcore.image.CoreImage;

public class Layer1ImageManager {

	public static final int TILE_WIDTH = 50;
	public static final int TILE_HEIGHT = 50;
	
	private static Layer1ImageManager instance;
	
	public static Layer1ImageManager getInstance(){
		
		if( instance == null ){
			instance = new Layer1ImageManager(); 
		}
		
		return instance;
	}
	
	private Map images;
	
	public Map getImages() {
		return images;
	}

	/**
	 * A = water
	 * B = ground
	 * G = mountains
	 * J = road
	 * K = big cities
	 * L = small cities
	 * N = navigation route
	 * P = main cities
	 */
	private Layer1ImageManager(){
		images = new HashMap();
		load("A");
		load("B");
		load("G");
		load("J");
		load("K");
		load("L");
		load("N");
		load("P");
	}

	private void load(String string) {
		images.put(string, CoreImage.load( string+ ".png"));
	}
}
