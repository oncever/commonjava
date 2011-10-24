package org.commonjava;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

	public static Properties load(String file){
		Properties equipe = new Properties();
		try {
			equipe.load(new FileInputStream(file));
			return equipe;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Map<String, String> loadMap(Properties p){
		Map<String, String> map =new HashMap<String, String>();
		Enumeration<?> e = p.propertyNames();
		while(e.hasMoreElements()){
			Object nextElement = e.nextElement();
			map.put((String)nextElement, p.getProperty((String) nextElement));
		}
		return map;
	}
}
