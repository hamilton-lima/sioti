package com.sioti.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import pulpcore.net.Upload;

import com.sioti.util.Log;

public class Layer1DataService extends BaseService{

	private static Layer1DataService instance;
	private String serviceName = "layer1_around.php";

	private Layer1DataService() {
			try {
				setUpload(new Upload(new URL(getHost() + serviceName )));
			} catch (MalformedURLException e) {
				Log.error(e, "error setting layer1 url");
			}
	}

	public static Layer1DataService getInstance() {
		if( instance == null ){
			instance = new Layer1DataService();
		}
		return instance;
	}

	
	public String getData(int x, int y) {
		Log.debug("layer1 get data");

		getUpload().addField("x", Integer.toString(x) );
		getUpload().addField("y", Integer.toString(y) );

		try {
			getUpload().sendNow();
			String response = getUpload().getResponse();
			return response;
			
		} catch (IOException e) {
			Log.error(e, "IO error in layer1");
		}

		return "";
	}

}
