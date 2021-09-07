package com.ramsey.rexam.beans;

import com.ramsey.rexam.entity.Question;
import com.ramsey.rexam.exception.QuestionNotFoundError;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

@Dependent
public class QuestionBean extends Bean {
	
	private EntityManager em;
	
	@PostConstruct
	public void init() {
		
		super.init();
		
	}
	
	public Boolean addQuestion(Question question) {
		
		getEm().getTransaction().begin();
		getEm().persist(question);
		getEm().getTransaction().commit();
		
		if(question.getId() != null) {
			
			try {
				
				return getQuestion(question.getId()) != null;
				
			} catch(QuestionNotFoundError ex) {
				
				return false;
				
			}
			
		}
		
		return false;
		
	}
	
	public List<Question> getQuestions() {
		
		CriteriaBuilder cb = getEm().getCriteriaBuilder();
		CriteriaQuery<Question> cq = cb.createQuery(Question.class);
		Root<Question> root = cq.from(Question.class);
		cq.select(root);
		return getEm().createQuery(cq).getResultList();
		
	}
	
	public List<Question> getQuestions(String question) {
		
		CriteriaBuilder cb = getEm().getCriteriaBuilder();
		CriteriaQuery<Question> cq = cb.createQuery(Question.class);
		Root<Question> root = cq.from(Question.class);
		cq.select(root);
		cq.where(cb.like(cb.lower(root.get("question")), String.format("%%%s%%", question.toLowerCase())));
		List<Question> questions = getEm().createQuery(cq).getResultList();
		
		if(questions == null || questions.isEmpty()) {
			
			throw new QuestionNotFoundError(question);
			
		}
		
		return questions;
		
	}
	
	public Question getQuestion(Long questionId) {
		
		CriteriaBuilder cb = getEm().getCriteriaBuilder();
		CriteriaQuery<Question> cq = cb.createQuery(Question.class);
		Root<Question> root = cq.from(Question.class);
		cq.select(root);
		cq.where(cb.equal(root.get("id"), questionId));
		
		try {
			
			return getEm().createQuery(cq).getSingleResult();
			
		} catch(NoResultException ex) {
			
			throw new QuestionNotFoundError(questionId);
			
		}
		
	}
	
	public Question updateQuestion(Long questionId, Question question) {
		
		Question oldQuestion = getQuestion(questionId);
		oldQuestion.copy(question);
		getEm().getTransaction().begin();
		getEm().merge(oldQuestion);
		getEm().getTransaction().commit();
		return oldQuestion;
		
	}
	
	public Boolean deleteQuestion(Long questionId) {
		
		Question question = getQuestion(questionId);
		getEm().getTransaction().begin();
		getEm().remove(question);
		getEm().getTransaction().commit();
		
		try {
			
			getQuestion(questionId);
			
		} catch(QuestionNotFoundError ex) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	@PreDestroy
	public void clear() {
		
		super.clear();
		
	}
	
}
