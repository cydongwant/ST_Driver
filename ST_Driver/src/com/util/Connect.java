package com.util;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

public class Connect {
	static int timeOut = 8*1000;
	static int soTimeOut = 7* 5000;

	public static HttpClient getHttpClient() {
		// TODO Auto-generated method stub
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, timeOut);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		HttpClient client = new DefaultHttpClient(httpParams);
        return client;
	}
}
