package org.commonjava.collection;

import java.util.Collection;

public class ArraySubOrderedList<T> extends ArraySubList<T> implements SubOrderedList<T>{

	private String order;
	
	public ArraySubOrderedList() {
		super();
	}

	public ArraySubOrderedList(Collection<? extends T> c, int totalSize, int offset, String order) {
		super(c, totalSize, offset);
		this.order = order;
	}

	@Override
	public String getOrder() {
		return order;
	}

}
