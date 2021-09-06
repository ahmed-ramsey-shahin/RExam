package com.ramsey.rexam.beans;

import com.ramsey.rexam.entity.Exam;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

@Dependent
public class ExamBean {
	
	private EntityManager em;
	private static final String persistenceUnitName = "RExamPU";
	
	@PostConstruct
	public void init() {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
		em = emf.createEntityManager();
		
	}
	
	public List<Exam> getExams() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Exam> cq = cb.createQuery(Exam.class);
		Root<Exam> root = cq.from(Exam.class);
		cq.select(root);
		return em.createQuery(cq).getResultList();
		
	}
	
	public List<Exam> getExams(String examName) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Exam> cq = cb.createQuery(Exam.class);
		Root<Exam> root = cq.from(Exam.class);
		cq.select(root);
		cq.where(cb.like(root.get("name"), String.format("%%%s%%", examName)));
		return em.createQuery(cq).getResultList();
		
	}
	
	public Exam getExam(Long examId) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Exam> cq = cb.createQuery(Exam.class);
		Root<Exam> root = cq.from(Exam.class);
		cq.select(root);
		cq.where(cb.equal(root.get("id"), examId));
		return em.createQuery(cq).getSingleResult();
		
	}
	
	@PreDestroy
	public void clean() {
		
		em.close();
		
	}
	
}
