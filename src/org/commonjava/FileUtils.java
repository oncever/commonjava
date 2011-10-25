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

	public static void copy(String from, String to, Map<String, String> replace, List<String> fileTypes, Map<String, String> replaceFileName){
		File fileFrom 	= new File(from);
		File fileTo 	= new File(to);
		
		if(!fileFrom.exists()) throw new RuntimeException("O arquivo "+from+" não existe");
		if(fileTo.exists()){
			if(fileFrom.isDirectory() && fileTo.isFile()) throw new RuntimeException("O diretório "+to+" não pode ser criado porque um arquivo com esse nome já existe");
			if(fileFrom.isFile() && fileTo.isDirectory()) throw new RuntimeException("O arquivo "+to+" não pode ser criado porque um diretorio com esse nome já existe");
		}
		
		if(fileFrom.isDirectory()){
			if(!fileTo.exists()) fileTo.mkdir();
			for (File itemFrom : fileFrom.listFiles()) {
				File itemTo = null;
				if(replaceFileName.keySet().contains(itemFrom.getName())) 	itemTo = new File(to, replaceFileName.get(itemFrom.getName()));
				else														itemTo = new File(to, itemFrom.getName());
				copy(itemFrom.getAbsolutePath(), itemTo.getAbsolutePath(), replace, fileTypes, replaceFileName);
			}
		}else {
			String sufix = getSufix(fileFrom.getAbsolutePath());
			if(replace.isEmpty() || !fileFrom.getName().contains(".") || !fileTypes.contains(sufix)){
				InputStream in =null;
				OutputStream out =null;
				try {
					in = new FileInputStream(fileFrom);
					out = new FileOutputStream(fileTo);
					
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
					in = new FileReader(fileFrom);
					out = new FileWriter(fileTo);
					
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

	private static String getSufix(String path) {
		String substring = path.substring(path.lastIndexOf(".")+1, path.length());
		return substring;
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
