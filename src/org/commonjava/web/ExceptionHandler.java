package org.commonjava.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.commonjava.StringUtils;
import org.commonjava.exception.UserException;

import com.sun.faces.application.ActionListenerImpl;

/**
 * @author Jean
 * 
 */
public class ExceptionHandler extends ActionListenerImpl {
 
	public static final String EXCEPTIONS 		= "org.commonjava.web.exceptionHandler.exceptions";
	public static final String ADDITIONAL_LOGS 	= "org.commonjava.web.exceptionHandler.additionalLogs";
	
	@Override
	public void processAction(ActionEvent event) {
		try {
			super.processAction(event);
		} catch (Exception e) {
			showAllExceptionMessages(e);
		}
	}
	
	public void showAllExceptionMessages(Exception e) {
		e.printStackTrace();

		Throwable cause = e;
		while(cause !=null && !(cause instanceof UserException)) cause = cause.getCause();
		
		if(cause!=null)	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, cause.getMessage(), null));
		else{
			//TODO internacionalizar
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro ao tentar executar ação", null));
			logUnexpectedError(e);
		}
	}

	protected void logUnexpectedError(Exception e) {
		List<Map<String, Object>> list = JSFUtils.getApplicationAttribute(EXCEPTIONS);
		if(list==null){
			list = new ArrayList<Map<String,Object>>();
			JSFUtils.setApplicationAttribute(EXCEPTIONS, list);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date"		, new Date());
		map.put("exception"	, e);
		
		{
			// logando informações adicionais preenchidas no web.xml
			String initParameter = JSFUtils.getServletContext().getInitParameter(ADDITIONAL_LOGS);
			for (String item: initParameter.split(";")) {
				String[] nomeValor = StringUtils.splitOnFirst(item,"=");
				map.put(nomeValor[0], JSFUtils.getELValue(nomeValor[1]));
			}
		}
	}
}