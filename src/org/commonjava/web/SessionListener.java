package org.commonjava.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


@SuppressWarnings("unchecked")
public class SessionListener implements HttpSessionListener{
	
	public static final String SESSIONS = "org.commonjava.web.sessionListener.sessions";
	
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		List<HttpSession> sessoesAtivas =  (List<HttpSession>) session.getServletContext().getAttribute(SESSIONS);
		if (sessoesAtivas == null) {
			sessoesAtivas = new ArrayList<HttpSession>();
			session.getServletContext().setAttribute(SESSIONS, sessoesAtivas);
		}
		sessoesAtivas.add(event.getSession());
	}
	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		List<HttpSession> sessoesAtivas =  (List<HttpSession>) session.getServletContext().getAttribute(SESSIONS);
		sessoesAtivas.remove(session);
	}
}
