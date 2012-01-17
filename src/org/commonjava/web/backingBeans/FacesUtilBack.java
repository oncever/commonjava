package org.commonjava.web.backingBeans;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import org.commonjava.ReflectionUtils;
import org.commonjava.web.JSFUtils;

public class FacesUtilBack {

	public List<SelectItem> convertToSelectItem(Object listaOuArray){
		return convertToSelectItem(listaOuArray, null,null,null);
	}
	public List<SelectItem> convertToSelectItem(Object listaOuArray, String label){
		return convertToSelectItem(listaOuArray, label,null,null);
	}
	public List<SelectItem> convertToSelectItem(Object listaOuArray, String label, String value){
		return convertToSelectItem(listaOuArray, label,value,null);
	}
	public List<SelectItem> convertToSelectItem(Object listaOuArray, String label, String valuePropertie, String selecione){
		if(listaOuArray == null)
			return JSFUtils.convertToSelectItem(new Object[]{}, label, valuePropertie, selecione);
		if(listaOuArray instanceof Collection)
			return JSFUtils.convertToSelectItem(((List)listaOuArray).toArray(), label, valuePropertie, selecione);
		if(listaOuArray instanceof String) {
			Class<?> class1 = ReflectionUtils.forName((String)listaOuArray);
			if(!class1.isEnum()) throw new RuntimeException("Não é enum");
			try {
				Method method = class1.getMethod("values");
				return JSFUtils.convertToSelectItem((Object[])method.invoke(null), label, valuePropertie, selecione);
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}
		
		return JSFUtils.convertToSelectItem(((Object[])listaOuArray), label, valuePropertie, selecione);
	}
}
