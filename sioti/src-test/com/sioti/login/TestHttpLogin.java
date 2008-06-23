package com.sioti.login;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * para executar este teste, eh necessario que esteja criado o usuario foo.bar
 * no moodle e tenha acesso as roles mock1 e mock2 que devem ser criadas sem
 * direitos de acesso somente para a verificacao do mecanismo de login.
 * 
 * resumo de acoes no moodle : - criar usuario foo.bar - criar roles mock1 e
 * mock2 - associar usuario foo.bar as duas roles
 * 
 * @author HAL
 * 
 */
public class TestHttpLogin extends TestCase {

	public void testLogin() throws HttpException, IOException {

		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost("game.sioti.com", 80, "http");
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

		PostMethod auth = new PostMethod("/login.php");
		NameValuePair u = new NameValuePair("u", "foo.bar");
		NameValuePair p = new NameValuePair("p", "foo.bar");
		auth.setRequestBody(new NameValuePair[] { u, p });
		client.executeMethod(auth);
		
		assertEquals("MSG_AUTH_OK", auth.getResponseBodyAsString(1024));
		assertEquals("HTTP/1.1 200 OK", auth.getStatusLine().toString());

		auth.releaseConnection();
	}

	public void testInvalidLogin() throws HttpException, IOException {

		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost("game.sioti.com", 80, "http");
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

		PostMethod auth = new PostMethod("/login.php");
		NameValuePair u = new NameValuePair("u", "foo.bar");
		NameValuePair p = new NameValuePair("p", "1foo.bar");
		auth.setRequestBody(new NameValuePair[] { u, p });
		client.executeMethod(auth);
		
		assertEquals("MSG_ERROR_INVALID_LOGIN", auth.getResponseBodyAsString(1024));
		assertEquals("HTTP/1.1 200 OK", auth.getStatusLine().toString());

		auth.releaseConnection();
	}

	public void testGetRole() throws HttpException, IOException {

		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost("game.sioti.com", 80, "http");
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

		PostMethod auth = new PostMethod("/login.php");
		NameValuePair u = new NameValuePair("u", "foo.bar");
		NameValuePair p = new NameValuePair("p", "foo.bar");
		auth.setRequestBody(new NameValuePair[] { u, p });
		client.executeMethod(auth);

		PostMethod getRoles = new PostMethod("/get_roles.php");
		client.executeMethod(getRoles);
		
		assertEquals("HTTP/1.1 200 OK", getRoles.getStatusLine().toString());
		assertEquals("mock1,mock2", getRoles.getResponseBodyAsString(1024));

		auth.releaseConnection();
	}

	public void testGetSetup() throws HttpException, IOException {

		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost("game.sioti.com", 80, "http");
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

		PostMethod auth = new PostMethod("/login.php");
		NameValuePair u = new NameValuePair("u", "foo.bar");
		NameValuePair p = new NameValuePair("p", "foo.bar");
		auth.setRequestBody(new NameValuePair[] { u, p });
		client.executeMethod(auth);

		PostMethod getRoles = new PostMethod("/get_setup.php");
		client.executeMethod(getRoles);
		
		assertEquals("HTTP/1.1 200 OK", getRoles.getStatusLine().toString());
		assertEquals("chunk_width=80;chunk_height=60;", getRoles.getResponseBodyAsString(1024));

		auth.releaseConnection();
	}

}
