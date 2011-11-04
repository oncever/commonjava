package org.commonjava.web;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.PageContext;

public class JSPUtils {

	/**
	 * JspApplicationContext 	jspApplicationContext 	= JspFactory.getDefaultFactory().getJspApplicationContext(getServletContext());
	 * PageContext 				pageContext 			= JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 0, true);
	 * @param el
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getELValue(String el, JspApplicationContext jspApplicationContext, PageContext pageContext) {
		try {
			ELContext 				elContext 				= pageContext.getELContext();
			ExpressionFactory 		expressionFactory 		= jspApplicationContext.getExpressionFactory();
			ValueExpression 		exp 					= expressionFactory.createValueExpression(elContext, el, Object.class);
			Object 					elValue 				= exp.getValue(elContext);
			return (T) elValue;
		} catch (Exception e) {
			throw new RuntimeException("Erro ao tentar resolver a el: "+el,e);
		}
	}
}
