package org.commonjava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class FileUtils {

	public static void copyDir(String from, String to, Map<String, String> replace, List<String> fileTypes){
		File fileFrom 	= new File(from);
		File fileTo 	= new File(to);
		
		if(!fileFrom.exists()) throw new RuntimeException("O diretório "+from+" não existe");
		if(fileTo.exists() && fileTo.isFile()) throw new RuntimeException("O diretório "+from+" não pode ser criado porque um arquivo com esse nome já existe");
		if(!fileTo.exists()) fileTo.mkdir();
		
		for (File itemFrom : fileFrom.listFiles()) {
			File itemTo = new File(to, itemFrom.getName());
			String path = itemFrom.getAbsolutePath();
			if(itemFrom.isDirectory()){
				copyDir(path, itemTo.getAbsolutePath(), replace, fileTypes);
			}else{
				if(replace.isEmpty() || !itemFrom.getName().contains(".") || !fileTypes.contains(path.substring(path.indexOf("."), path.length()))){
					InputStream in =null;
					OutputStream out =null;
					try {
						in = new FileInputStream(itemFrom);
						out = new FileOutputStream(itemTo);
						
						byte[] buf = new byte[1024];
						int len;
						while ((len = in.read(buf)) > 0){
							out.write(buf, 0, len);
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					} finally{
						try { if(in!=null) in.close(); } catch (IOException e) {throw new RuntimeException(e);}
						try { if(out!=null) out.close();} catch (IOException e) {throw new RuntimeException(e);}
					}
				}else {
				}
			}
		}
	}
	
	public static void copyExtruture(String from, String to){
		for (File itemFrom : new File(from).listFiles()) {
			if(itemFrom.isDirectory()){
				File itemTo = new File(to, itemFrom.getName());
				itemTo.mkdir();
				copyExtruture(itemFrom.getAbsolutePath(), itemTo.getAbsolutePath());
			}
		}
	}
}
