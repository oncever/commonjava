package org.commonjava.convert;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

public class StringConverter {
	@SuppressWarnings("unchecked")
	public static <T> T convert(Class<T> type, String s) {
		
		if (type.equals(String.class)) return (T) s;
		if (s == null || s.equals(""))return null;
		
		if (type.equals(Byte		.class) || type.equals(Byte			.TYPE))	return (T) new Byte		(s);
		if (type.equals(Character	.class) || type.equals(Character	.TYPE))	return (T) new Character(s.charAt(0));
		if (type.equals(Short		.class) || type.equals(Short		.TYPE))	return (T) new Short	(s);
		if (type.equals(Integer		.class) || type.equals(Integer		.TYPE))	return (T) new Integer	(s);
		if (type.equals(Long		.class) || type.equals(Long			.TYPE))	return (T) new Long		(s);
		if (type.equals(Float		.class) || type.equals(Float		.TYPE))	return (T) new Float	(s);
		if (type.equals(Double		.class) || type.equals(Double		.TYPE))	return (T) new Double	(s);
		if (type.equals(Boolean		.class) || type.equals(Boolean		.TYPE))	return (T) new Boolean	(s);
		if (type.equals(Object		.class)) return (T) s;
			
		throw new RuntimeException("Tipo não encontrado: " + type);
	}

	@SuppressWarnings("unchecked")
	public static <T> T convertI18n(Class<T> type, String s) {
			if (type.equals(String.class)) return (T) s;
			if (s == null || s.equals("")) return null;

		try {
			if (type.equals(Byte		.class) || type.equals(Byte		.TYPE))	return (T) new Byte(s);
			if (type.equals(Character	.class) || type.equals(Character.TYPE))	return (T) new Character(s.charAt(0));
			if (type.equals(Short		.class) || type.equals(Short	.TYPE))	return (T) (Short	) NumberFormat.getInstance().parse(s).shortValue();
			if (type.equals(Integer		.class) || type.equals(Integer	.TYPE))	return (T) (Integer	) NumberFormat.getInstance().parse(s).intValue();
			if (type.equals(Long		.class) || type.equals(Long		.TYPE))	return (T) (Long	) NumberFormat.getInstance().parse(s).longValue();
			if (type.equals(Float		.class) || type.equals(Float	.TYPE))	return (T) (Float	) NumberFormat.getInstance().parse(s).floatValue();
			if (type.equals(Double		.class) || type.equals(Double	.TYPE))	return (T) (Double	) NumberFormat.getInstance().parse(s).doubleValue();
			if (type.equals(Boolean		.class) || type.equals(Boolean	.TYPE))	return (T) new Boolean(s);
			if (type.equals(Date		.class)) return (T) DateFormat.getDateInstance().parse(s);
			if (type.equals(Object		.class)) return (T) s;
				
			throw new RuntimeException("Tipo não encontrado: " + type);
		} catch (NumberFormatException e) {
			throw new RuntimeException(e);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}