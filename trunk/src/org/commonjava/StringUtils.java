package org.commonjava;

import java.util.Map;

public class StringUtils {
	
	public static String firstUpper(String a){
		if(a==null) return null;
		if(a.isEmpty()) return a;
		if(a.length()==1) return a.toUpperCase();
		return a.substring(0,1).toUpperCase()+a.substring(1, a.length()-1);
	}
	public static String firstLower(String a){
		if(a==null) return null;
		if(a.isEmpty()) return a;
		if(a.length()==1) return a.toLowerCase();
		return a.substring(0,1).toLowerCase()+a.substring(1, a.length()-1);
	}
	public static String replaceAll(String a, Map<String, String> map){
		for (String key : map.keySet())
			a = a.replaceAll(key, map.get(key));
		return a;
	}

}
