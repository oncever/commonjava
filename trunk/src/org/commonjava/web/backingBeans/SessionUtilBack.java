package org.commonjava.web.backingBeans;

import javax.servlet.http.HttpSession;

import org.commonjava.web.JSFUtils;

public class SessionUtilBack {

	public String logout(){
		HttpSession session = JSFUtils.getSession(false);
		if(session!=null)	session.invalidate();
		return "logout";
	}
}
