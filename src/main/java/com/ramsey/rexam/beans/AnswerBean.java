package com.ramsey.rexam.beans;

import com.ramsey.rexam.entity.Answer;
import com.ramsey.rexam.exception.AnswerNotFoundError;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Dependent
public class AnswerBean extends Bean {
	
	@PostConstruct
	public void init() {
		
		super.init();
		
	}
	
	public Boolean addAnswer(Answer answer) {
		
		getEm().getTransaction().begin();
		getEm().persist(answer);
		getEm().getTransaction().commit();
		
		if(answer.getId() != null) {
			
			try {
				
				getAnswer(answer.getId());
				
			} catch(AnswerNotFoundError ex) {
				
				return false;
				
			}
			
		}
		
		return false;
		
	}
	
	public Answer getAnswer(Long answerId) {
		
		CriteriaBuilder cb = getEm().getCriteriaBuilder();
		CriteriaQuery<Answer> cq = cb.createQuery(Answer.class);
		Root<Answer> root = cq.from(Answer.class);
		cq.select(root);
		cq.where(cb.equal(root.get("id"), answerId));
		
		try {
			
			return getEm().createQuery(cq).getSingleResult();
			
		} catch(NoResultException ex) {
			
			throw new AnswerNotFoundError(answerId);
			
		}
		
	}
	
	@PreDestroy
	public void clean() {
		
		super.clean();
		
	}
	
}
