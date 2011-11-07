package org.commonjava.collection;

import java.util.List;

public interface SubList<T> extends List<T>
{
	public int getTotalSize();
	public int getOffset();
}
