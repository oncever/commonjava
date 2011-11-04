package org.commonjava.web.backingBeans;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class DownloadUtilBack {

	public void downloadString(String s, String filename){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");
		try {
			ServletOutputStream out = response.getOutputStream();
			out.write(s.getBytes());
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		context.responseComplete();
	}
}
