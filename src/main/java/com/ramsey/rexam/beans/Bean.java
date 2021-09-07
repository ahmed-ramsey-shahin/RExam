package com.ramsey.rexam.beans;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract class Bean {
	
	private EntityManager em;
	
	public void init() {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("RExamPU");
		em = emf.createEntityManager();
		
	}
	
	public void clear() {
		
		if(em.isOpen()) {
			
			em.close();
			
		}
		
	}
	
	public EntityManager getEm() {
		
		return em;
		
	}
	
}
