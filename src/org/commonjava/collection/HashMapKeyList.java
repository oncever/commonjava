package org.commonjava.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link HashMap} que armazena os chaves na ordem em que foram adicionados
 * 
 * @see #keyList()
 * @author Jean
 */
public class HashMapKeyList<T, R> extends HashMap<T, R> implements MapKeyList<T, R>{
	
	private static final long serialVersionUID = 1L;

	List<T> keyList = new ArrayList<T>();
	
	public HashMapKeyList() {
		super();
	}

	public HashMapKeyList(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public HashMapKeyList(int initialCapacity) {
		super(initialCapacity);
	}

	public HashMapKeyList(Map<? extends T, ? extends R> m) {
		super(m);
	}

	public R put(T key, R value) {
		keyList.remove(key);
		keyList.add(key);
		return super.put(key, value);
	};
	
	@Override
	public R remove(Object key) {
		keyList.remove(key);
		return super.remove(key);
	}
	
	/**
	 * @return As chaves do Map na ordem em que foram adicionadas
	 */
	@Override
	public List<T> keyList() {
		return keyList;
	}
	
	
}