package com.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {
	public static boolean ifwifi(Context c) {
		ConnectivityManager manager = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		manager.getAllNetworkInfo();
		manager.getNetworkInfo(0);
		if (info.isAvailable()) {
			int type = info.getType();
			if (type == ConnectivityManager.TYPE_WIFI) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNetActive(Context c) {
		ConnectivityManager manager = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();

		if (info.isAvailable())
			return true;
		return false;
	}
}
