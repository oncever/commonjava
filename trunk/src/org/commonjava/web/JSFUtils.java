package org.commonjava.web;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
}