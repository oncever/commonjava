package org.commonjava;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.commonjava.collection.HashMapKeyList;
import org.commonjava.collection.MapKeyList;

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
	
	public static void save(Properties p, String file){
		Writer out = null;
		try {
			out = new FileWriter(file);
			p.store(out, null);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}finally{
			try { if(out!=null) out.close(); } catch (IOException e) {throw new RuntimeException(e);}
		}
	}
	
	public static MapKeyList<String, String> loadMap(Properties p){
		MapKeyList<String, String> map =new HashMapKeyList<String, String>();
		Enumeration<?> e = p.propertyNames();
		while(e.hasMoreElements()){
			Object nextElement = e.nextElement();
			map.put((String)nextElement, p.getProperty((String) nextElement));
		}
		return map;
	}
	
	public static Properties load(Map<String, String> map){
		Properties p = new Properties();
		for (String item : map.keySet()) {
			p.put(item, map.get(item));
		}
		return p;
	}
}
