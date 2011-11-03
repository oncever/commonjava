package org.commonjava.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


@SuppressWarnings("unchecked")
public class SessionListener implements HttpSessionListener{
	
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		List<HttpSession> sessoesAtivas =  (List<HttpSession>) session.getServletContext().getAttribute("sessoes");
		if (sessoesAtivas == null) {
			sessoesAtivas = new ArrayList<HttpSession>();
			session.getServletContext().setAttribute("sessoes", sessoesAtivas);
		}
		sessoesAtivas.add(event.getSession());
	}
	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		List<HttpSession> sessoesAtivas =  (List<HttpSession>) session.getServletContext().getAttribute("sessoes");
		sessoesAtivas.remove(session);
	}
}
