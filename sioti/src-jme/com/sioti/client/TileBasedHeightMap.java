package com.sioti.client;

import com.jmex.terrain.util.AbstractHeightMap;

public class TileBasedHeightMap extends AbstractHeightMap {

	/**
	 * 
	 * 
	 * constructor of the class
	 * @param data
	 * @param lines
	 * @param columns
	 * @param converter
	 */
	public TileBasedHeightMap(String data, int lines, int columns, StringToHeightConverter converter) {
		
		// TODO Auto-generated constructor stub
	}
	
	public boolean load() {
		// TODO Auto-generated method stub
		return doLoad();
	}

	/**
	 * tenta carregar o mapa baseado nas informacoes recebidas
	 * na construcao do objeto
	 * 
	 * @return
	 */
	private boolean doLoad() {

		boolean done = false;
		
		// clean up
        if (null != heightData) {
            unloadHeightMap();
        }

        // initialize the height data attributes
        heightData = new int[size * size];	
        
        return done;
	
	}

	
}
