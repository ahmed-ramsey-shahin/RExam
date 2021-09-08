package com.ramsey.rexam.beans;

import com.ramsey.rexam.entity.Question;
import com.ramsey.rexam.exception.QuestionNotFoundError;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class QuestionBean extends Bean {

	private static QuestionBean instance;
	
	private QuestionBean() {
		
		init();
		
	}
	
	public static QuestionBean getInstance() {
		
		if(instance == null) {
			
			instance = new QuestionBean();
			
		}
		
		return instance;
		
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
		
		if(oldQuestion != question) {
			
			oldQuestion.copy(question);
			
		}
		
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
	
	public void clean() {
		
		instance = null;
		super.clean();
		
	}
	
}
