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

import java.util.List;

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
	
	public List<Answer> getAnswers() {
		
		CriteriaBuilder cb = getEm().getCriteriaBuilder();
		CriteriaQuery<Answer> cq = cb.createQuery(Answer.class);
		Root<Answer> root = cq.from(Answer.class);
		cq.select(root);
		return getEm().createQuery(cq).getResultList();
		
	}
	
	public List<Answer> getAnswers(String answer) {
		
		CriteriaBuilder cb = getEm().getCriteriaBuilder();
		CriteriaQuery<Answer> cq = cb.createQuery(Answer.class);
		Root<Answer> root = cq.from(Answer.class);
		cq.select(root);
		cq.where(cb.like(cb.lower(root.get("text")), String.format("%%%s%%", answer.toLowerCase())));
		List<Answer> answers = getEm().createQuery(cq).getResultList();
		
		if(answers == null || answers.isEmpty()) {
			
			throw new AnswerNotFoundError(answer);
			
		}
		
		return answers;
		
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
	
	public Answer updateAnswer(Long answerId, Answer answer) {
		
		Answer oldAnswer = getAnswer(answerId);
		
		if(oldAnswer != answer) {
			
			oldAnswer.setText(answer.getText());
			
		}
		
		getEm().getTransaction().begin();
		getEm().merge(oldAnswer);
		getEm().getTransaction().commit();
		return oldAnswer;
		
	}
	
	public Boolean deleteAnswer(Long answerId) {
		
		Answer answer = getAnswer(answerId);
		getEm().getTransaction().begin();
		getEm().remove(answer);
		getEm().getTransaction().commit();
		
		try {
			
			getAnswer(answerId);
			
		} catch(AnswerNotFoundError ex) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	@PreDestroy
	public void clean() {
		
		super.clean();
		
	}
	
}
