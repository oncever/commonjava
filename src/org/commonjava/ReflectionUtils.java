package org.commonjava;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária para trabalhar com Reflection.
 */
public class ReflectionUtils {

	
	public static <T> T newInstance(Class<T> clazz){
		try{
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("Impossível instanciar "+clazz, e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Impossível instanciar "+clazz, e);
		}
	}
	
	public static <T> T newInstance(Class<T> clazz, Object... args) {
		try {
			Class<?>[] argsClass = null;
			if (args != null) {
				argsClass = new Class<?>[args.length];
			}

			return clazz.getConstructor(argsClass).newInstance(args);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static Class<?> forName(String className){
		try {
			return (Class<?>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Field forFieldName(String classField){
		try {
			String clazzName = classField.substring(0, classField.lastIndexOf('.'));
			String fieldName = classField.substring(classField.lastIndexOf('.') + 1);
			Field field = forName(clazzName).getDeclaredField(fieldName);
			return field;
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Method getFieldGetter(Field field) {
		try {
			return field.getDeclaringClass().getMethod(getGetterName(field.getName()));
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public static Method getFieldSetter(Field field)  {
		try {
			return field.getDeclaringClass().getMethod(getSetterName(field.getName()), field.getType());
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getGetterName(String fieldName) {
		return "get" + StringUtils.firstUpper(fieldName);
	}

	public static String getGetterIsName(String fieldName) {
		return "is" + StringUtils.firstUpper(fieldName);
	}

	public static String getSetterName(String fieldName) {
		return "set" + StringUtils.firstUpper(fieldName);
	}
	
	public static Field[] getNonStaticFields(Class<?> clazz){
		List<Field> campos = new ArrayList<Field>();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if(!Modifier.isStatic(field.getModifiers())){
				campos.add(field);
			}
		}
		return campos.toArray(new Field[0]);
	}
	
	
	public static String[] getNonStaticFieldNames(Class<?> clazz){
		List<String> campos = new ArrayList<String>();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if(!Modifier.isStatic(field.getModifiers())){
				campos.add(field.getName());
			}
		}
		return campos.toArray(new String[0]);
	}
	
	public static Method getMethodByName(Object instance, String methodName) {
		Class<? extends Object> class1 = instance.getClass();

		while(class1!=null){
			Method[] declaredMethods = class1.getDeclaredMethods();
			for (Method method : declaredMethods) {
				if(method.getName().equals(methodName)){
					return method;
				}
			}
			class1 = class1.getSuperclass();
		}
		throw new IllegalArgumentException("Método "+methodName+" não existe em "+instance.getClass());
	}
	
	/**
	 * Para que este método funcione, o campo passado como parâmetro deve ser do
	 * tipo List
	 * 
	 * @param listField
	 * @return O tipo genérico da lista do campo passado como parametro
	 */
	public static Class<?> getFirstParameterizedType(Field listField) {
		return getFirstParameterizedType(listField.getGenericType());
	}

	public static Class<?> getFirstParameterizedType(Type type) {
		ParameterizedType pt = (ParameterizedType) type;
		Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
		return genericClazz;
	}
	
	public static Method getFieldGetter(Class<?> clazz, String multiLevelName) {
		try {
			return clazz.getMethod(getGetterName(multiLevelName));
		} catch (NoSuchMethodException e1) {
			try {
				return clazz.getMethod(getGetterIsName(multiLevelName));
			} catch (NoSuchMethodException e2) {
					throw new IllegalArgumentException("Getter não encontrado para '" + multiLevelName + "' em " + clazz + "!", e2);
			}
		}
	}
	
	public static Object execute(Object obj, String string) {
		try {
			Method method = obj.getClass().getMethod(string);
			Object invoke = method.invoke(obj);
			return invoke;
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Field getFieldWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClazz) {
		for (Field field : clazz.getDeclaredFields()) {
			Annotation ann = field.getAnnotation(annotationClazz);
			if (ann != null) {
				return field;
			}
		}
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null)
			return getFieldWithAnnotation(superClazz, annotationClazz);
		return null;
	}
	public static Field[] getFieldsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClazz) {
		ArrayList<Field> fields = new ArrayList<Field>();
		addFieldsWithAnnotation(clazz, annotationClazz, fields);
		return fields.toArray(new Field[0]);
	}
	private static void addFieldsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClazz, List<Field> fields) {
		for (Field field : clazz.getDeclaredFields()) {
			Annotation annotation = field.getAnnotation(annotationClazz);
			if (annotation != null) {
				fields.add(field);
			}
		}
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null) {
			addFieldsWithAnnotation(superClazz, annotationClazz, fields);
		}
	}

	public static Object executeGetterMethod(String name, Object obj) {
		return execute(obj, getGetterName(name));
	}

}