package org.commonjava.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class JPAUtils {

	private static Map<String, JPAUtils> instance = new HashMap<String, JPAUtils>();
	public static JPAUtils getInstance(String pu){
		JPAUtils jpaUtils = instance.get(pu);
		if(jpaUtils==null){
			jpaUtils = new JPAUtils(pu);
			instance.put(pu, jpaUtils);
		}
		return jpaUtils;
	}
	
	
	private EntityManagerFactory emf;
	
	private JPAUtils(String pu){
		emf = Persistence.createEntityManagerFactory(pu);
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}
	
	
}
