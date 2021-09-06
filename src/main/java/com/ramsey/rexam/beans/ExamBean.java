package com.ramsey.rexam.beans;

import com.ramsey.rexam.entity.Exam;
import com.ramsey.rexam.exception.ExamNotFoundError;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
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
	
	public Boolean addExam(Exam exam) {
		
		em.getTransaction().begin();
		em.persist(exam);
		em.getTransaction().commit();
		
		if(exam.getId() != null) {
			
			try {
				
				return getExam(exam.getId()) != null;
				
			} catch(ExamNotFoundError ex) {
				
				return false;
				
			}
			
		}
		
		return false;
		
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
		cq.where(cb.like(cb.lower(root.get("name")), String.format("%%%s%%", examName.toLowerCase())));
		List<Exam> exams = em.createQuery(cq).getResultList();
		
		if(exams == null || exams.isEmpty()) {
			
			throw new ExamNotFoundError(examName);
			
		}
		
		return exams;
		
	}
	
	public Exam getExam(Long examId) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Exam> cq = cb.createQuery(Exam.class);
		Root<Exam> root = cq.from(Exam.class);
		cq.select(root);
		cq.where(cb.equal(root.get("id"), examId));
		
		try {
			
			return em.createQuery(cq).getSingleResult();
			
		} catch(NoResultException ex) {
			
			throw new ExamNotFoundError(examId);
			
		}
		
	}
	
	public Exam updateExam(Long examId, Exam exam) {
		
		Exam oldExam = getExam(examId);
		oldExam.copy(exam);
		em.getTransaction().begin();
		em.merge(oldExam);
		em.getTransaction().commit();
		return oldExam;
		
	}
	
	public Boolean deleteExam(Long examId) {
		
		em.getTransaction().begin();
		Exam exam = getExam(examId);
		em.remove(exam);
		em.getTransaction().commit();
		
		try {
			
			getExam(examId);
			
		} catch(ExamNotFoundError ex) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	@PreDestroy
	public void clean() {
		
		em.close();
		
	}
	
}
