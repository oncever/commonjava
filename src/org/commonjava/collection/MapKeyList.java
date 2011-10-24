package org.commonjava.collection;

import java.util.List;
import java.util.Map;

/**
 * {@link Map} que armazena os chaves na ordem em que foram adicionados
 * 
 * @see #keyList()
 * @author Jean
 */
public interface MapKeyList<T, R> extends Map<T, R>{
	
	/**
	 * @return As chaves do Map na ordem em que foram adicionadas
	 */
	List<T> keyList();
	
	
}