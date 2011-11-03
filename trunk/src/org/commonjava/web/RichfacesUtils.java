package org.commonjava.web;

import java.util.HashSet;
import java.util.Set;

public class RichfacesUtils {

	public static void addReRender(String... reRender){
		for (String string : reRender) {
			addReRender(string);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void addReRender(String reRender){
		Object requestAtual = JSFUtils.getRequest().getAttribute("reRender");
		if (requestAtual==null) {
			requestAtual = new HashSet<String>();
			JSFUtils.getRequest().setAttribute("reRender", requestAtual);
		}else if (requestAtual instanceof String) {
			String string = (String) requestAtual;
			requestAtual = new HashSet<String>();
			((Set<String>)requestAtual).add(string);
			JSFUtils.getRequest().setAttribute("reRender", requestAtual);
		}
		String[] split = reRender.split(",");
		for (String item : split) {
			((Set<String>)requestAtual).add(item.trim());
		}
	}
}
