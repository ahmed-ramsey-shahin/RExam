package com.ramsey.rexam.beans;

import com.ramsey.rexam.entity.Exam;
import com.ramsey.rexam.exception.ExamNotFoundError;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

@Dependent
public class ExamBean extends Bean {
	
	@PostConstruct
	public void init() {
		
		super.init();
		
	}
	
	public Boolean addExam(Exam exam) {
		
		getEm().getTransaction().begin();
		getEm().persist(exam);
		getEm().getTransaction().commit();
		
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
		
		CriteriaBuilder cb = getEm().getCriteriaBuilder();
		CriteriaQuery<Exam> cq = cb.createQuery(Exam.class);
		Root<Exam> root = cq.from(Exam.class);
		cq.select(root);
		return getEm().createQuery(cq).getResultList();
		
	}
	
	public List<Exam> getExams(String examName) {
		
		CriteriaBuilder cb = getEm().getCriteriaBuilder();
		CriteriaQuery<Exam> cq = cb.createQuery(Exam.class);
		Root<Exam> root = cq.from(Exam.class);
		cq.select(root);
		cq.where(cb.like(cb.lower(root.get("name")), String.format("%%%s%%", examName.toLowerCase())));
		List<Exam> exams = getEm().createQuery(cq).getResultList();
		
		if(exams == null || exams.isEmpty()) {
			
			throw new ExamNotFoundError(examName);
			
		}
		
		return exams;
		
	}
	
	public Exam getExam(Long examId) {
		
		CriteriaBuilder cb = getEm().getCriteriaBuilder();
		CriteriaQuery<Exam> cq = cb.createQuery(Exam.class);
		Root<Exam> root = cq.from(Exam.class);
		cq.select(root);
		cq.where(cb.equal(root.get("id"), examId));
		
		try {
			
			return getEm().createQuery(cq).getSingleResult();
			
		} catch(NoResultException ex) {
			
			throw new ExamNotFoundError(examId);
			
		}
		
	}
	
	public Exam updateExam(Long examId, Exam exam) {
		
		Exam oldExam = getExam(examId);
		
		if(oldExam != exam) {
			
			oldExam.copy(exam);
			
		}
		
		getEm().getTransaction().begin();
		getEm().merge(oldExam);
		getEm().getTransaction().commit();
		return oldExam;
		
	}
	
	public Boolean deleteExam(Long examId) {
		
		getEm().getTransaction().begin();
		Exam exam = getExam(examId);
		getEm().remove(exam);
		getEm().getTransaction().commit();
		
		try {
			
			getExam(examId);
			
		} catch(ExamNotFoundError ex) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	@PreDestroy
	public void clean() {
		
		super.clean();
		
	}
	
}
