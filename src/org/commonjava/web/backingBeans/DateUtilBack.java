package org.commonjava.web.backingBeans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.faces.context.FacesContext;

public class DateUtilBack {

	public TimeZone getTimeZone() {
	    return TimeZone.getDefault();
	}
	public String getShortPattern() {
		return ((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, FacesContext.getCurrentInstance().getViewRoot().getLocale())).toPattern();
	}
	public String getLongPattern() {
		return ((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG, FacesContext.getCurrentInstance().getViewRoot().getLocale())).toPattern();
	}
	public String getMediumPattern() {
		return ((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, FacesContext.getCurrentInstance().getViewRoot().getLocale())).toPattern();
	}
}
