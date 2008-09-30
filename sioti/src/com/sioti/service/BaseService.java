package com.sioti.service;

import pulpcore.net.Upload;

public class BaseService {

	private String host = "http://game.sioti.com/";

	private String sessionIdFieldName = "session_id";

	private Upload upload;

	// stores the session id for all services
	private static String session;

	public Upload getUpload() {
		return upload;
	}

	public void setUpload(Upload upload) {
		this.upload = upload;
		if (session != null) {
			upload.addField(sessionIdFieldName, session);
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public static void setSession(String s) {
		session = s;
	}

}
