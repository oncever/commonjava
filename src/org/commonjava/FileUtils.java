package org.commonjava;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileUtils {

	public static void copyDir(String from, String to, Map<String, String> replace, List<String> fileTypes, Map<String, String> replaceFileName){
		File fileFrom 	= new File(from);
		File fileTo 	= new File(to);
		
		if(!fileFrom.exists()) throw new RuntimeException("O diretório "+from+" não existe");
		if(fileTo.exists() && fileTo.isFile()) throw new RuntimeException("O diretório "+from+" não pode ser criado porque um arquivo com esse nome já existe");
		if(!fileTo.exists()) fileTo.mkdir();
		
		for (File itemFrom : fileFrom.listFiles()) {
			File itemTo = null;
			if(replaceFileName.keySet().contains(itemFrom.getName())) 	itemTo = new File(to, replaceFileName.get(itemFrom.getName()));
			else														itemTo = new File(to, itemFrom.getName());
			
			String path = itemFrom.getAbsolutePath();
			if(itemFrom.isDirectory()){
				copyDir(path, itemTo.getAbsolutePath(), replace, fileTypes, replaceFileName);
			}else{
				String substring = path.substring(path.lastIndexOf(".")+1, path.length());
				if(replace.isEmpty() || !itemFrom.getName().contains(".") || !fileTypes.contains(substring)){
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
					StringBuffer fileContent = new StringBuffer();
					String fileContentString = null;
					InputStreamReader in =null;
					OutputStreamWriter out =null;
					try {
						in = new FileReader(itemFrom);
						out = new FileWriter(itemTo);
						
						int charac;
						while(true){
							charac = in.read();
							if(charac==-1) break;
							fileContent.append((char) charac);
						}
						fileContentString = fileContent.toString();
						for (String key : replace.keySet()) {
							fileContentString = fileContentString.replaceAll(key, replace.get(key));
						}
						out.write(fileContentString);
					} catch (IOException e) {
						throw new RuntimeException(e);
					} finally{
						try { if(in!=null) in.close(); } catch (IOException e) {throw new RuntimeException(e);}
						try { if(out!=null) out.close();} catch (IOException e) {throw new RuntimeException(e);}
					}
				}
			}
		}
	}
	
	public static List<File> listChilds(File from, FileFilter filter){
		return listChilds(from, new ArrayList<File>(), filter);
	}
	private static List<File> listChilds(File file, List<File> files, FileFilter filter){
		for (File item :file.listFiles()) {
			if(filter.accept(item)){
				files.add(item);
				listChilds(item, files, filter);
			}
		}
		return files;
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
