package com.constant;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	public static int PAGE_INDEX = 0;
	public static String BASE_URL = "http://172.16.100.118:3000" ;
	public static String uuid;
	public static String[] STATUS_ORDER = {"idle","out","up","down","back"};
	public static Map<String ,String>  Statu_String = new HashMap<String, String>();
	public static Map<String ,String>  Statu_URI = new HashMap<String, String>();
	public static Map<String ,String>  Statu_Next = new HashMap<String, String>();
	static {
		Statu_String.put("out", "出战");
		Statu_String.put("up", "上车");
		Statu_String.put("down", "下车");
		Statu_String.put("back", "回站");
	}
	static {
		Statu_URI.put("out", "/driver_apps/:uuid/way_bills/:way_bill_id/out");
		Statu_URI.put("up", "/driver_apps/:uuid/way_bills/:way_bill_id/up");
		Statu_URI.put("down", "/driver_apps/:uuid/way_bills/:way_bill_id/down");
		Statu_URI.put("back", "/driver_apps/:uuid/way_bills/:way_bill_id/back");
	}
	static {
		Statu_URI.put("out", "up");
		Statu_URI.put("up", "down");
		Statu_URI.put("down", "back");
		Statu_URI.put("back", "out");
	}
	public static String CURRENT_STATU ;

}
