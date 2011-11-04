package org.commonjava.web.backingBeans;

import org.commonjava.exception.StackTraceUtil;

public class StackTraceUtilBack {

	public String getStackTrace(Throwable aThrowable) {
		return StackTraceUtil.getStackTrace(aThrowable);
	}
}
