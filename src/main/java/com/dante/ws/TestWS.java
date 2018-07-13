package com.dante.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestWS {
	public static void main(String[] args) {
		String uri = "http://localhost:8080/TheDawn/ws/test/postMethod";
		testWithHttpPost(uri);
	}

	public static void testWithHttpGet() {
		String uri = "http://localhost:8080/TheDawn/ws/test/10";
		HttpGet get = new HttpGet(uri);
		HttpClient client = new DefaultHttpClient();

		try {
			HttpResponse response = client.execute(get);

			System.out.println("\nSending 'GET' request to URL : " + uri);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			System.out.println("Result: " + result.toString());
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public static void testWithHttpPost(String uri) {
		
		HttpPost get = new HttpPost(uri);
		HttpClient client = new DefaultHttpClient();
		
		try {
			HttpResponse response = client.execute(get);
			
			System.out.println("\nSending 'GET' request to URL : " + uri);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			System.out.println("Result: " + result.toString());
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
