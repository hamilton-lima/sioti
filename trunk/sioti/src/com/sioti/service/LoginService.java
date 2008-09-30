package com.sioti.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import pulpcore.net.Upload;

import com.sioti.util.Log;

public class LoginService extends BaseService {

	private static LoginService instance;

	private static final String MSG_INVALID = "-1";

	private String serviceName = "login.php";
	
	private LoginService() {
		try {
			setUpload(new Upload(new URL(getHost() + serviceName)));
		} catch (MalformedURLException e) {
			Log.error(e, "error setting login url");
		}
	}

	public static LoginService getInstance() {
		if (instance == null) {
			instance = new LoginService();
		}

		return instance;
	}

	public void login(String user, String password) {
		Log.debug("login with user : " + user);

		getUpload().addField("u", user);
		getUpload().addField("p", password);

		try {
			getUpload().sendNow();
			String response = getUpload().getResponse();

			if (! response.equals(MSG_INVALID)) {
				BaseService.setSession(response);
			} else {
				BaseService.setSession(null);
			}

		} catch (IOException e) {
			Log.error(e, "IO error in login");
		}

	}

}
