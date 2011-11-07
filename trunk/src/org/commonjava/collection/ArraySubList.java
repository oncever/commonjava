package org.commonjava.collection;

import java.util.ArrayList;
import java.util.Collection;

public class ArraySubList<T> extends ArrayList<T> implements SubList<T>
{
	private static final long serialVersionUID = 1L;
	
	private int totalSize = 0;
	
	/** a partir de que item da lista inteira começa o sublist */
	private int offset = 0;
	
	public ArraySubList() {
		super();
	}

	public ArraySubList(Collection<? extends T> c, int totalSize, int offset) {
		super(c);
		this.totalSize 	= totalSize;
		this.offset 	= offset;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public int getOffset() {
		return offset;
	}

	@Override
	public void clear() {
		totalSize 	= 0;
		offset 		= 0;
		super.clear();
	}
	
	public void add(int index, T element) {
		super.add(index, element);
		totalSize++;
	};
	
	public boolean add(T e) {
		boolean add = super.add(e);
		totalSize++;
		return add;
	};
	
	@Override
	public T remove(int index) {
		T remove = super.remove(index);
		totalSize--;
		return remove;
	}
	
	@Override
	public boolean remove(Object o) {
		boolean remove = super.remove(o);
		totalSize--;
		return remove;
	}
	
	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean addAll = super.addAll(c);
		totalSize+=c.size();
		return addAll;
	}
}
