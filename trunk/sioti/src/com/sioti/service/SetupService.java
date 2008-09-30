package com.sioti.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import pulpcore.net.Upload;

import com.sioti.util.Log;

public class SetupService extends BaseService{

	private static SetupService instance;
	private String serviceName = "get_setup.php";

	private SetupService() {
			try {
				setUpload(new Upload(new URL(getHost() + serviceName )));
			} catch (MalformedURLException e) {
				Log.error(e, "error setting get_setup url");
			}
	}

	public static SetupService getInstance() {
		if( instance == null ){
			instance = new SetupService();
		}
		return instance;
	}

	
	public void getData() {
		Log.debug("get setup");

		try {
			getUpload().sendNow();
			String response = getUpload().getResponse();
			Setup.getInstance().init(response);
			
		} catch (IOException e) {
			Log.error(e, "IO error in get_setup");
		}

	}

}
