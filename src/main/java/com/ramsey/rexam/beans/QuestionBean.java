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
	
	@PreDestroy
	public void clear() {
		
		super.clear();
		
	}
	
}
