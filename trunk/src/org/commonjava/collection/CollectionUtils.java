package org.commonjava.collection;

import java.util.HashMap;
import java.util.Map;

public class CollectionUtils {

	public static Map<String, String> getStringMap(String... s){
		if(s.length%2!=0) throw new RuntimeException("Número ímpar de argumentos");
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < s.length; i+=2) {
			map.put(s[i], s[i+1]);
		}
		return map;
	}
}
