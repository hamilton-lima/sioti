package com.sioti.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;

public class TestCreateProfissao extends TestCase {

	public void testCreateProfissao() throws HttpException, IOException {
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

		PostMethod create = new PostMethod("/profissao_create.php");
		NameValuePair p1 = new NameValuePair("nome", "profissao test");
		NameValuePair p2 = new NameValuePair("descricao", "teste automatizado");
		NameValuePair p3 = new NameValuePair("nivel_maximo", "999");
		create.setRequestBody(new NameValuePair[] { p1, p2, p3 });
		client.executeMethod(create);

		assertEquals("HTTP/1.1 200 OK", create.getStatusLine().toString());
		String response = create.getResponseBodyAsString(1024);
		JSONObject map = JSONObject.fromObject(response);

		String id = (String) map.get("id");
		assertNotSame("0", id);

		// check if data was recorded correctly
		PostMethod read = new PostMethod("/profissao_read.php");
		NameValuePair pid = new NameValuePair("id", id);
		read.setRequestBody(new NameValuePair[] { pid });
		client.executeMethod(read);

		assertEquals("HTTP/1.1 200 OK", read.getStatusLine().toString());
		response = read.getResponseBodyAsString(1024);
		JSONArray dataArray = JSONArray.fromObject(response);
		assertEquals(1, dataArray.size());

		JSONObject one = (JSONObject) dataArray.get(0);
		assertEquals("profissao test", one.get("nome"));
		assertEquals("teste automatizado", one.get("descricao"));
		assertEquals("999", one.get("nivel_maximo"));

		// remove a profissao criada
		PostMethod delete = new PostMethod("/profissao_delete.php");
		client.executeMethod(delete);

		assertEquals("HTTP/1.1 200 OK", delete.getStatusLine().toString());
		response = delete.getResponseBodyAsString(1024);
		assertEquals("MSG_ERROR_PROFISSAO_DELETE_NO_ID", response);

		// agora com o ID =)
		delete = new PostMethod("/profissao_delete.php");
		NameValuePair did = new NameValuePair("id", id);
		delete.setRequestBody(new NameValuePair[] { did });
		client.executeMethod(delete);

		assertEquals("HTTP/1.1 200 OK", delete.getStatusLine().toString());
		response = delete.getResponseBodyAsString(1024);
		assertEquals("MSG_PROFISSAO_DELETE_OK", response);

		auth.releaseConnection();
	}

	public void testCreate2Profissao() throws HttpException, IOException {
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

		PostMethod create = new PostMethod("/profissao_create.php");
		NameValuePair p1 = new NameValuePair("nome", "profissao test");
		NameValuePair p2 = new NameValuePair("descricao", "teste automatizado");
		NameValuePair p3 = new NameValuePair("nivel_maximo", "999");
		create.setRequestBody(new NameValuePair[] { p1, p2, p3 });
		client.executeMethod(create);

		assertEquals("HTTP/1.1 200 OK", create.getStatusLine().toString());
		String response = create.getResponseBodyAsString(1024);
		JSONObject map = JSONObject.fromObject(response);

		String id = (String) map.get("id");
		assertNotSame("0", id);

		ArrayList idList = new ArrayList();
		idList.add(id);

		// add the second test line
		client.executeMethod(create);

		assertEquals("HTTP/1.1 200 OK", create.getStatusLine().toString());
		response = create.getResponseBodyAsString(1024);
		map = JSONObject.fromObject(response);

		id = (String) map.get("id");
		assertNotSame("0", id);
		idList.add(id);

		// check if data was recorded correctly
		PostMethod read = new PostMethod("/profissao_read.php");
		client.executeMethod(read);

		assertEquals("HTTP/1.1 200 OK", read.getStatusLine().toString());
		response = read.getResponseBodyAsString();
		JSONArray dataArray = JSONArray.fromObject(response);

		Iterator iterator = dataArray.iterator();
		Iterator iteratorId = idList.iterator();
		int found = 0;

		System.out.println("SIZE = " + dataArray.size());
		while (iterator.hasNext()) {
			JSONObject element = (JSONObject) iterator.next();
			System.out.println("id=" + element.get("id"));
			iteratorId = idList.iterator();
			while (iteratorId.hasNext()) {
				String oneId = (String) iteratorId.next();
				System.out.println("id=" + element.get("id"));
				if (element.get("id").equals(oneId)) {
					found++;
				}
			}
		}
		assertEquals(2, found);

		// remove as profissoes criadas
		iteratorId = idList.iterator();
		while (iteratorId.hasNext()) {
			String oneId = (String) iteratorId.next();

			PostMethod delete = new PostMethod("/profissao_delete.php");
			NameValuePair did = new NameValuePair("id", oneId);
			delete.setRequestBody(new NameValuePair[] { did });
			client.executeMethod(delete);

			assertEquals("HTTP/1.1 200 OK", delete.getStatusLine().toString());
			response = delete.getResponseBodyAsString(1024);
			assertEquals("MSG_PROFISSAO_DELETE_OK", response);
		}

		auth.releaseConnection();
	}

	public void testCreateUpdateProfissao() throws HttpException, IOException {
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

		PostMethod create = new PostMethod("/profissao_create.php");
		NameValuePair p1 = new NameValuePair("nome", "profissao test");
		NameValuePair p2 = new NameValuePair("descricao", "teste automatizado");
		NameValuePair p3 = new NameValuePair("nivel_maximo", "999");
		create.setRequestBody(new NameValuePair[] { p1, p2, p3 });
		client.executeMethod(create);

		assertEquals("HTTP/1.1 200 OK", create.getStatusLine().toString());
		String response = create.getResponseBodyAsString(1024);
		JSONObject map = JSONObject.fromObject(response);

		String id = (String) map.get("id");
		assertNotSame("0", id);

		// AlTERA os dados recem criados
		PostMethod update = new PostMethod("/profissao_update.php");
		client.executeMethod(update);

		assertEquals("HTTP/1.1 200 OK", update.getStatusLine().toString());
		response = update.getResponseBodyAsString(1024);
		assertEquals("MSG_ERROR_PROFISSAO_UPDATE_NO_ID", response);

		// agora com o ID =)
		update = new PostMethod("/profissao_update.php");
		NameValuePair did = new NameValuePair("id", id);
		p1 = new NameValuePair("nome", "A profissao test");
		p2 = new NameValuePair("descricao", "A teste automatizado");
		p3 = new NameValuePair("nivel_maximo", "111");
		update.setRequestBody(new NameValuePair[] { did, p1, p2, p3 });
		client.executeMethod(update);

		assertEquals("HTTP/1.1 200 OK", update.getStatusLine().toString());
		response = update.getResponseBodyAsString(1024);
		assertEquals("MSG_PROFISSAO_UPDATE_OK", response);

		// check if data was recorded correctly
		PostMethod read = new PostMethod("/profissao_read.php");
		NameValuePair pid = new NameValuePair("id", id);
		read.setRequestBody(new NameValuePair[] { pid });
		client.executeMethod(read);

		assertEquals("HTTP/1.1 200 OK", read.getStatusLine().toString());
		response = read.getResponseBodyAsString(1024);
		JSONArray dataArray = JSONArray.fromObject(response);
		assertEquals(1, dataArray.size());

		JSONObject one = (JSONObject) dataArray.get(0);
		assertEquals("A profissao test", one.get("nome"));
		assertEquals("A teste automatizado", one.get("descricao"));
		assertEquals("111", one.get("nivel_maximo"));

		// remove a profissao criada
		PostMethod delete = new PostMethod("/profissao_delete.php");
		client.executeMethod(delete);

		assertEquals("HTTP/1.1 200 OK", delete.getStatusLine().toString());
		response = delete.getResponseBodyAsString(1024);
		assertEquals("MSG_ERROR_PROFISSAO_DELETE_NO_ID", response);

		// agora com o ID =)
		delete = new PostMethod("/profissao_delete.php");
		did = new NameValuePair("id", id);
		delete.setRequestBody(new NameValuePair[] { did });
		client.executeMethod(delete);

		assertEquals("HTTP/1.1 200 OK", delete.getStatusLine().toString());
		response = delete.getResponseBodyAsString(1024);
		assertEquals("MSG_PROFISSAO_DELETE_OK", response);

		auth.releaseConnection();
	}

	public void testAddIconeProfissao() throws HttpException, IOException {
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

		PostMethod create = new PostMethod("/profissao_create.php");
		NameValuePair p1 = new NameValuePair("nome", "profissao test");
		NameValuePair p2 = new NameValuePair("descricao", "teste automatizado");
		NameValuePair p3 = new NameValuePair("nivel_maximo", "999");
		create.setRequestBody(new NameValuePair[] { p1, p2, p3 });
		client.executeMethod(create);

		assertEquals("HTTP/1.1 200 OK", create.getStatusLine().toString());
		String response = create.getResponseBodyAsString(1024);
		JSONObject map = JSONObject.fromObject(response);

		String id = (String) map.get("id");
		assertNotSame("0", id);

		// AlTERA os dados recem criados
		PostMethod update = new PostMethod("/profissao_add_icone.php");
		client.executeMethod(update);

		assertEquals("HTTP/1.1 200 OK", update.getStatusLine().toString());
		response = update.getResponseBodyAsString(1024);
		assertEquals("MSG_ERROR_PROFISSAO_ADD_ICONE_NO_ID", response);

		// agora com o ID =)
		update = new PostMethod("/profissao_add_icone.php");
		NameValuePair did = new NameValuePair("id", id);
		p1 = new NameValuePair("id_midia_icone", "34");
		update.setRequestBody(new NameValuePair[] { did, p1 });
		client.executeMethod(update);

		assertEquals("HTTP/1.1 200 OK", update.getStatusLine().toString());
		response = update.getResponseBodyAsString(1024);
		assertEquals("MSG_PROFISSAO_ADD_ICONE_OK", response);

		// check if data was recorded correctly
		PostMethod read = new PostMethod("/profissao_read.php");
		NameValuePair pid = new NameValuePair("id", id);
		read.setRequestBody(new NameValuePair[] { pid });
		client.executeMethod(read);

		assertEquals("HTTP/1.1 200 OK", read.getStatusLine().toString());
		response = read.getResponseBodyAsString(1024);
		JSONArray dataArray = JSONArray.fromObject(response);
		assertEquals(1, dataArray.size());

		JSONObject one = (JSONObject) dataArray.get(0);
		assertEquals("34", one.get("id_midia_icone"));


		// remove o icone adicionado 
		update = new PostMethod("/profissao_delete_icone.php");
		client.executeMethod(update);

		assertEquals("HTTP/1.1 200 OK", update.getStatusLine().toString());
		response = update.getResponseBodyAsString(1024);
		assertEquals("MSG_ERROR_PROFISSAO_DELETE_ICONE_NO_ID", response);

		// agora com o ID =)
		update = new PostMethod("/profissao_delete_icone.php");
		did = new NameValuePair("id", id);
		update.setRequestBody(new NameValuePair[] { did});
		client.executeMethod(update);

		assertEquals("HTTP/1.1 200 OK", update.getStatusLine().toString());
		response = update.getResponseBodyAsString(1024);
		assertEquals("MSG_PROFISSAO_DELETE_ICONE_OK", response);

		// check if data was recorded correctly
		read = new PostMethod("/profissao_read.php");
		pid = new NameValuePair("id", id);
		read.setRequestBody(new NameValuePair[] { pid });
		client.executeMethod(read);

		assertEquals("HTTP/1.1 200 OK", read.getStatusLine().toString());
		response = read.getResponseBodyAsString(1024);
		dataArray = JSONArray.fromObject(response);
		assertEquals(1, dataArray.size());

		one = (JSONObject) dataArray.get(0);
		assertEquals("0", one.get("id_midia_icone"));
		
		// remove a profissao criada
		PostMethod delete = new PostMethod("/profissao_delete.php");
		client.executeMethod(delete);

		assertEquals("HTTP/1.1 200 OK", delete.getStatusLine().toString());
		response = delete.getResponseBodyAsString(1024);
		assertEquals("MSG_ERROR_PROFISSAO_DELETE_NO_ID", response);

		// agora com o ID =)
		delete = new PostMethod("/profissao_delete.php");
		did = new NameValuePair("id", id);
		delete.setRequestBody(new NameValuePair[] { did });
		client.executeMethod(delete);

		assertEquals("HTTP/1.1 200 OK", delete.getStatusLine().toString());
		response = delete.getResponseBodyAsString(1024);
		assertEquals("MSG_PROFISSAO_DELETE_OK", response);

		auth.releaseConnection();
	}
	
	
}
