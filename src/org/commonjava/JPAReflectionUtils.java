package org.commonjava;

import java.lang.reflect.Field;

import javax.persistence.Id;

public class JPAReflectionUtils {
	@SuppressWarnings("unchecked")
		public static <T> T executeIdGetterMethod(Object obj){
			Class<? extends Object> clazz = obj.getClass();
			Field[] fields = ReflectionUtils.getFieldsWithAnnotation(clazz, Id.class);
			if(fields.length==0) throw new IllegalArgumentException("A classe "+clazz.getSimpleName()+" não possui nenhum field com a annotation id");
			if(fields.length >1) throw new IllegalArgumentException("A classe "+clazz.getSimpleName()+" possui pk multipla");
			Object idValue = ReflectionUtils.executeGetterMethod(fields[0].getName(), obj);
			return (T) idValue;
		}
}

