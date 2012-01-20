package org.commonjava.web;

import java.util.LinkedList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.commonjava.ReflectionUtils;

public class JSFUtils {

	public static void setELValue(String el, Object value) {
		FacesContext context   = FacesContext.getCurrentInstance();
		Application  facesApp  = context.getApplication();
		ELContext    elContext = context.getELContext();
		ExpressionFactory expressionFactory = facesApp.getExpressionFactory();
		
		ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
		
		exp.setValue(elContext, value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getELValue(String el) {
		try {
			FacesContext context   = FacesContext.getCurrentInstance();
			Application  facesApp  = context.getApplication();
			ELContext    elContext = context.getELContext();
			ExpressionFactory expressionFactory = facesApp.getExpressionFactory();
			
			ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
			
			return (T) exp.getValue(elContext);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao tentar resolver a el: "+el,e);
		}
	}
	
	public static ServletContext getServletContext() {
		return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	}
	
	public static HttpSession getSession(boolean create) {
		return getRequest().getSession(create);
	}
	
	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getApplicationAttribute(String name) {
		return (T) getServletContext().getAttribute(name);
	}

	public static void setApplicationAttribute(String name, Object value) {
		getServletContext().setAttribute(name, value);
	}
	
	public static void executeEL(String el) {
		FacesContext context   = FacesContext.getCurrentInstance();
		Application  facesApp  = context.getApplication();
		ELContext    elContext = context.getELContext();
		ExpressionFactory expressionFactory = facesApp.getExpressionFactory();
		
		MethodExpression exp = expressionFactory.createMethodExpression(elContext, el, null, new Class<?>[]{});
		exp.invoke(elContext, null);
	}
	
	public static HttpServletRequest getRequest() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		return (HttpServletRequest) externalContext.getRequest();
	}
	
	public static Integer getIntRequestParam(String name) {
		String parameter = getRequest().getParameter(name);
		if(parameter==null||parameter.isEmpty()) return null;
		return Integer.valueOf(parameter);
	}
	
	public static Long getLongRequestParam(String name) {
		String parameter = getRequest().getParameter(name);
		if(parameter==null||parameter.isEmpty()) return null;
		return Long.valueOf(parameter);
	}
	
	public static String getRequestParam(String name) {
		return getRequest().getParameter(name);
	}

	/**
	 * Converte um array de um objeto qualquer para uma lista de selectItems.
	 * @param objetos a lista de objetos a para ser convertida
	 * @param labelPropertie a propriedade do objeto que vai ser o label de selectItem. Se esta propriedade for null, o toString ser� usado como label
	 * @param valuePropertie a propriedade do objeto que vai ser a valor do selectItem. Se esta propriedade for null, o pr�prio objeto ser� usado como value
	 * @param firstLabel labael padr�o com valor null. Se est� propriedade for null, a lista n�o tera um valor padr�o null.
	 * @return uma lista de selectItems
	 * @author Jean Baldessar
	 */
	public static List<SelectItem> convertToSelectItem(Object[] objetos, String labelPropertie, String valuePropertie, String firstLabel){
		List<SelectItem> retorno = new LinkedList<SelectItem>();
		if (firstLabel!=null) retorno.add(new SelectItem(null, firstLabel) );
		
		for (Object item : objetos) {
			String label = null;
			if (labelPropertie!=null) {
				Object labelObject = ReflectionUtils.executeGetterMethod(labelPropertie, item);
				label = String.valueOf(labelObject);
			}else {
				label = item.toString();
			}
			
			Object value = null;
			if (valuePropertie!=null) {
				value = ReflectionUtils.executeGetterMethod(valuePropertie, item);
			}else {
				value = item;
			}
			retorno.add( new SelectItem(value, label) );
		}
		return retorno;
	}
}
