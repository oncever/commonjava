package org.commonjava.collection;

import java.util.List;

public interface OrderedList<T> extends List<T>
{
	public String getOrder();
}
