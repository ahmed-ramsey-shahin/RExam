package com.ramsey.rexam.entity;

import jakarta.persistence.*;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "QUESTION")
public class Question implements Serializable {
	
	private static final long serialVersionUID = -2303494646424526554L;
	
	@Id
	@SequenceGenerator(
			name = "questionSequenceGenerator",
			allocationSize = 1,
			sequenceName = "questionSequenceGenerator"
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "questionSequenceGenerator"
	)
	@Column(updatable = false)
	private Long id;
	
	@Column(unique = true)
	private String question;
	
	@Min(0)
	private Integer score;
	
	@ManyToMany(cascade = {CascadeType.PERSIST})
	private List<Answer> answers;
	
	@JoinColumn(nullable = false)
	@ManyToOne(optional = false, cascade = {CascadeType.PERSIST})
	private Answer rightAnswer;
	
	public Question(Long id, String question, Integer score) {
		
		this.id = id;
		this.question = question;
		this.score = score;
		
	}
	
	public Question() {  }
	
	public Long getId() {
		
		return id;
		
	}
	
	public void setId(Long id) {
		
		this.id = id;
		
	}
	
	public String getQuestion() {
		
		return question;
		
	}
	
	public void setQuestion(String question) {
		
		this.question = question;
		
	}
	
	public Integer getScore() {
		
		return score;
		
	}
	
	public void setScore(Integer score) {
		
		this.score = score;
		
	}
	
	public List<Answer> getAnswers() {
		
		return answers;
		
	}
	
	public void setAnswers(List<Answer> answers) {
		
		this.answers = answers;
		
	}
	
	public Answer getRightAnswer() {
		
		return rightAnswer;
		
	}
	
	public void setRightAnswer(Answer rightAnswer) {
		
		this.rightAnswer = rightAnswer;
		
	}
	
	public void copy(Question question) {
		
		setQuestion(question.getQuestion());
		setScore(question.getScore());
		getAnswers().clear();
		question.getAnswers().forEach(getAnswers()::add);
		
		if(question.getRightAnswer() != null) {
			
			setRightAnswer(question.getRightAnswer());
			
		}
		
	}
	
}
