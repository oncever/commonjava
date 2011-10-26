package org.commonjava;

public class StringUtils {
	
	public static String firstUpper(String a){
		if(a==null) return null;
		if(a.isEmpty()) return a;
		if(a.length()==1) return a.toUpperCase();
		return a.substring(0,1).toUpperCase()+a.substring(a.length()-1);
	}
	public static String firstLower(String a){
		if(a==null) return null;
		if(a.isEmpty()) return a;
		if(a.length()==1) return a.toLowerCase();
		return a.substring(0,1).toLowerCase()+a.substring(a.length()-1);
	}

}
