package cn.sdk.cache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

public class TestHttpClinet {
	public static void main(String args[]) throws ClientProtocolException,
			IOException {
		String url = "http://192.168.10.138/bilinserver/loginBiLinUser.html?username=18610646715&password=b537a06cf3bcb33206237d7149c27bc3&version=3.3.0";
		
		HttpClient client = new DefaultHttpClient();
		
		HttpGet request = new HttpGet(url);
		request.addHeader("Accept-Encoding","gzip, deflate");
		
		HttpResponse response = client.execute(request);
		System.out.println("Response Code: " + response.getStatusLine().getStatusCode());
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String line = "";
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
		}

		
		
	}
}
