package org.commonjava;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	public static String firstUpper(String a){
		if(a==null) return null;
		if(a.isEmpty()) return a;
		if(a.length()==1) return a.toUpperCase();
		return a.substring(0,1).toUpperCase()+a.substring(1, a.length());
	}
	
	public static String firstLower(String a){
		if(a==null) return null;
		if(a.isEmpty()) return a;
		if(a.length()==1) return a.toLowerCase();
		return a.substring(0,1).toLowerCase()+a.substring(1, a.length());
	}
	
	public static String replaceAll(String a, Map<String, String> map){
		for (String key : map.keySet())
			a = a.replaceAll(key, map.get(key));
		return a;
	}
	
	public static String[] splitOnFirst(String a, String split){
		if(a==null) return null;
		int indexOf = a.indexOf(split);
		if(a.isEmpty()||a.length()==1||indexOf==-1) return new String[]{a};
		return new String[]{a.substring(0, indexOf), a.substring(indexOf, a.length())};
	}
	
	/**
	 * <p>Converte uma String para o padrão ASCII2.</p>
	 * <p>
	 *    Exemplo:
	 *    <table border="1">        
	 *       <tr>
	 *       	<td><b>Original</b></td>
	 *       	<td><b>ASCII2</b></td>
	 *       </tr>
	 *       <tr>
	 *       	<td>Oração</td>
	 *       	<td>Oracao</td>
	 *       </tr>
	 *       <tr>
	 *       	<td>Utilitário</td>
	 *       	<td>Utilitario</td>
	 *       </tr>
	 *    </table>
	 * </p>
	 * 
	 * @param text O texto a ser convertido.
	 * @return O texto no padrão ASCII2.
	 */
	public static String convertToASCII2(String text) {
		return text.replaceAll("[ãâàáä]", "a").replaceAll("[ÃÂÀÁÄ]", "A")
				   .replaceAll("[êèéë]" , "e").replaceAll("[ÊÈÉË]" , "E")
				   .replaceAll("[îìíï]" , "i").replaceAll("[ÎÌÍÏ]" , "I")
				   .replaceAll("[õôòóö]", "o").replaceAll("[ÕÔÒÓÖ]", "O")
				   .replaceAll("[ûúùü]" , "u").replaceAll("[ÛÙÚÜ]" , "U")
				   .replace('ç', 'c').replace('Ç', 'C')
				   .replace('ñ', 'n').replace('Ñ', 'N');
	}
	
	public static String convertToIdentifier(String text) {
		Matcher m = Pattern.compile(" (\\w)").matcher(text);
		m.reset();
		boolean result = m.find();
		if (result) {
		    StringBuffer sb = new StringBuffer();
		    do {
		        m.appendReplacement(sb, m.group(1).toUpperCase());
		        result = m.find();
		    } while (result);
		    m.appendTail(sb);
		    return sb.toString();
		}
		return text;
	}

}
