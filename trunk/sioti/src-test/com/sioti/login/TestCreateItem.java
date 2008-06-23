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

public class TestCreateItem extends TestCase {

	// CREATE TABLE sioti_item (
	// id int(10) unsigned NOT NULL auto_increment,
	// id_tipo_item int default '0',
	// nome varchar(50) default '0',
	// nivel_requerido int default '0',
	// atributos varchar(50) default '0',
	// nivel int default '0',
	// id_midia_icone int default '0',
	// id_midia_no_jogo int default '0',
	//
	// PRIMARY KEY (id)
	// );

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

		// id_tipo_item int default '0',
		// nome varchar(50) default '0',
		// nivel_requerido int default '0',
		// atributos varchar(50) default '0',
		// nivel int default '0',

		PostMethod create = new PostMethod("/item_create.php");
		NameValuePair p1 = new NameValuePair("id_tipo_item", "1");
		NameValuePair p2 = new NameValuePair("nome", "teste automatizado");
		NameValuePair p3 = new NameValuePair("nivel_requerido", "999");
		NameValuePair p4 = new NameValuePair("atributos", "+3STA -5STR");
		NameValuePair p5 = new NameValuePair("nivel", "333");
		create.setRequestBody(new NameValuePair[] { p1, p2, p3, p4, p5 });
		client.executeMethod(create);

		assertEquals("HTTP/1.1 200 OK", create.getStatusLine().toString());
		String response = create.getResponseBodyAsString(1024);
		JSONObject map = JSONObject.fromObject(response);

		String id = (String) map.get("id");
		assertNotSame("0", id);

		// check if data was recorded correctly
		PostMethod read = new PostMethod("/item_read.php");
		NameValuePair pid = new NameValuePair("id", id);
		read.setRequestBody(new NameValuePair[] { pid });
		client.executeMethod(read);

		assertEquals("HTTP/1.1 200 OK", read.getStatusLine().toString());
		response = read.getResponseBodyAsString(1024);
		System.out.println("response = " + response);
		JSONArray dataArray = JSONArray.fromObject(response);
		assertEquals(1, dataArray.size());

		JSONObject one = (JSONObject) dataArray.get(0);
		assertEquals("1", one.get("id_tipo_item"));
		assertEquals("teste automatizado", one.get("nome"));
		assertEquals("999", one.get("nivel_requerido"));
		assertEquals("+3STA -5STR", one.get("atributos"));
		assertEquals("333", one.get("nivel"));

		// remove a profissao criada
		PostMethod delete = new PostMethod("/item_delete.php");
		client.executeMethod(delete);

		assertEquals("HTTP/1.1 200 OK", delete.getStatusLine().toString());
		response = delete.getResponseBodyAsString(1024);
		assertEquals("MSG_ERROR_ITEM_DELETE_NO_ID", response);

		// agora com o ID =)
		delete = new PostMethod("/item_delete.php");
		NameValuePair did = new NameValuePair("id", id);
		delete.setRequestBody(new NameValuePair[] { did });
		client.executeMethod(delete);

		assertEquals("HTTP/1.1 200 OK", delete.getStatusLine().toString());
		response = delete.getResponseBodyAsString(1024);
		assertEquals("MSG_ITEM_DELETE_OK", response);

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

		PostMethod create = new PostMethod("/item_create.php");
		NameValuePair p1 = new NameValuePair("id_tipo_item", "1");
		NameValuePair p2 = new NameValuePair("nome", "teste automatizado");
		NameValuePair p3 = new NameValuePair("nivel_requerido", "999");
		NameValuePair p4 = new NameValuePair("atributos", "+3STA -5STR");
		NameValuePair p5 = new NameValuePair("nivel", "333");
		create.setRequestBody(new NameValuePair[] { p1, p2, p3, p4, p5 });
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
		PostMethod read = new PostMethod("/item_read.php");
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

			PostMethod delete = new PostMethod("/item_delete.php");
			NameValuePair did = new NameValuePair("id", oneId);
			delete.setRequestBody(new NameValuePair[] { did });
			client.executeMethod(delete);

			assertEquals("HTTP/1.1 200 OK", delete.getStatusLine().toString());
			response = delete.getResponseBodyAsString(1024);
			assertEquals("MSG_ITEM_DELETE_OK", response);
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

		PostMethod create = new PostMethod("/item_create.php");
		NameValuePair p1 = new NameValuePair("id_tipo_item", "1");
		NameValuePair p2 = new NameValuePair("nome", "teste automatizado");
		NameValuePair p3 = new NameValuePair("nivel_requerido", "999");
		NameValuePair p4 = new NameValuePair("atributos", "+3STA -5STR");
		NameValuePair p5 = new NameValuePair("nivel", "333");
		create.setRequestBody(new NameValuePair[] { p1, p2, p3, p4, p5 });
		client.executeMethod(create);

		assertEquals("HTTP/1.1 200 OK", create.getStatusLine().toString());
		String response = create.getResponseBodyAsString(1024);
		JSONObject map = JSONObject.fromObject(response);

		String id = (String) map.get("id");
		assertNotSame("0", id);
		System.out.println("id=" + id);

		// AlTERA os dados recem criados
		PostMethod update = new PostMethod("/item_update.php");
		client.executeMethod(update);

		assertEquals("HTTP/1.1 200 OK", update.getStatusLine().toString());
		response = update.getResponseBodyAsString(1024);
		assertEquals("MSG_ERROR_ITEM_UPDATE_NO_ID", response);

		// agora com o ID =)
		update = new PostMethod("/item_update.php");
		NameValuePair did = new NameValuePair("id", id);
		p1 = new NameValuePair("id_tipo_item", "99");
		p2 = new NameValuePair("nome", "A teste automatizado");
		p3 = new NameValuePair("nivel_requerido", "222");
		p4 = new NameValuePair("atributos", "-3STA +5STR");
		p5 = new NameValuePair("nivel", "444");
		update.setRequestBody(new NameValuePair[] { did, p1, p2, p3, p4, p5 });
		client.executeMethod(update);

		assertEquals("HTTP/1.1 200 OK", update.getStatusLine().toString());
		response = update.getResponseBodyAsString(1024);
		assertEquals("MSG_ITEM_UPDATE_OK", response);

		// check if data was recorded correctly
		PostMethod read = new PostMethod("/item_read.php");
		NameValuePair pid = new NameValuePair("id", id);
		read.setRequestBody(new NameValuePair[] { pid });
		client.executeMethod(read);

		assertEquals("HTTP/1.1 200 OK", read.getStatusLine().toString());
		response = read.getResponseBodyAsString(1024);
		JSONArray dataArray = JSONArray.fromObject(response);
		assertEquals(1, dataArray.size());

		p3 = new NameValuePair("nivel_requerido", "222");
		p4 = new NameValuePair("atributos", "-3STA +5STR");
		p5 = new NameValuePair("nivel", "444");

		JSONObject one = (JSONObject) dataArray.get(0);
		assertEquals("A teste automatizado", one.get("nome"));
		assertEquals("99", one.get("id_tipo_item"));
		assertEquals("222", one.get("nivel_requerido"));
		assertEquals("-3STA +5STR", one.get("atributos"));
		assertEquals("444", one.get("nivel"));

		// remove a profissao criada
		PostMethod delete = new PostMethod("/item_delete.php");
		client.executeMethod(delete);

		assertEquals("HTTP/1.1 200 OK", delete.getStatusLine().toString());
		response = delete.getResponseBodyAsString(1024);
		assertEquals("MSG_ERROR_ITEM_DELETE_NO_ID", response);

		// agora com o ID =)
		delete = new PostMethod("/item_delete.php");
		did = new NameValuePair("id", id);
		delete.setRequestBody(new NameValuePair[] { did });
		client.executeMethod(delete);

		assertEquals("HTTP/1.1 200 OK", delete.getStatusLine().toString());
		response = delete.getResponseBodyAsString(1024);
		assertEquals("MSG_ITEM_DELETE_OK", response);

		auth.releaseConnection();
	}

}
