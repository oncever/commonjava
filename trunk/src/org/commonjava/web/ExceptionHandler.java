package org.commonjava.web;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.commonjava.exception.UserException;

import com.sun.faces.application.ActionListenerImpl;

/**
 * @author Jean
 * 
 */
public class ExceptionHandler extends ActionListenerImpl {
 
	public static final String EXCEPTIONS_SALVAS = "exceptionsSalvas";
	
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
		
	}
}