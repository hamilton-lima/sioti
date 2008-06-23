package com.sioti.map;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URL;
import java.net.URLConnection;

public class NetworkUtil {

	public static String getData(String layer1Service, int x, int y) {
		return get(layer1Service, "x=" + x + "&y=" + y);
	}

	/**
	 * send the data thru an http request string de resposta use
	 * URLEncoder.encode() when sending Strings
	 * 
	 * @author Hamilton Lima
	 * @version 19/12/2002
	 */
	private static String get(String destino, String content) {

		URL url;
		URLConnection cn;
		DataOutputStream out;
		DataInputStream in;

		try {
			url = new URL(destino );
			cn = url.openConnection();

			cn.setDoInput(true);
			cn.setDoOutput(true);
			cn.setUseCaches(false);

			out = new DataOutputStream(cn.getOutputStream());
			out.writeBytes(content);
			out.flush();
			out.close();

			in = new DataInputStream(cn.getInputStream());

			String str;
			StringBuffer resultado = new StringBuffer();

			str = in.readLine();

			while (str != null) {
				resultado.append(str);
				str = in.readLine();
			}

			in.close();
			return resultado.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

}
