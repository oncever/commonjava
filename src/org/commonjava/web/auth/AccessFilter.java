package org.commonjava.web.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AccessFilter implements Filter{

	private FilterConfig	filterConfig	= null;

    public void init(FilterConfig config) throws ServletException {
    	filterConfig = config;
    }
    
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest 	request 	= (HttpServletRequest) 	servletRequest;
		HttpServletResponse response 	= (HttpServletResponse) servletResponse;
		String 				url 		= request.getRequestURI();
		String 				contextPath = request.getContextPath();
		url = url.substring(contextPath.length());
		
		
		String loginPage = filterConfig.getInitParameter("org.commonjava.web.auth.loginPage");
		String errorPage = filterConfig.getInitParameter("org.commonjava.web.auth.errorPage");

		if(loginPage==null) throw new NullPointerException("loginPage � null");
		if(errorPage==null) throw new NullPointerException("errorPage � null");
		
		if(url.equals(loginPage)||url.equals(errorPage))
			filterChain.doFilter(servletRequest, servletResponse);
		else if(isAuthenticated(request, response))
			if(hasPermission(request, response))
				filterChain.doFilter(servletRequest, servletResponse);
			else
				filterConfig.getServletContext().getRequestDispatcher(errorPage).forward(request, response);
		else{
			// ignora requests recund�rios
			String requestPrimario = request.getHeader("referer");
			// quando vem de um action de um form, ele vem com o refer preenchido, ent�o essa segunda parta de express�o
			// faz com que o logout continue funcionando
			if(requestPrimario==null || request.getSession().getAttribute("org.commonjava.web.auth.requestPage") ==null)
				request.getSession().setAttribute("org.commonjava.web.auth.requestPage", contextPath+url);
			filterConfig.getServletContext().getRequestDispatcher(loginPage).forward(request, response);
		}
		
		
    }

	public void destroy() {}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}
	
	protected abstract boolean isAuthenticated(HttpServletRequest request, HttpServletResponse response);
	protected abstract boolean hasPermission(HttpServletRequest request, HttpServletResponse response);

}
