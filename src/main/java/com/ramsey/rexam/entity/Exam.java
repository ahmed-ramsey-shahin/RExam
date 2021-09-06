package com.ramsey.rexam.entity;

import jakarta.persistence.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Exam")
@Table(name = "EXAM")
public class Exam implements Serializable {
	
	private static final long serialVersionUID = -5135136267151550865L;
	
	@Id
	@SequenceGenerator(
			name = "examSequenceGenerator",
			allocationSize = 1,
			sequenceName = "examSequenceGenerator"
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "examSequenceGenerator"
	)
	@Column(updatable = false)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String name;
	
	@Column(nullable = false)
	@Min(0) @Max(100)
	private Double passingScore;
	
	@Column(nullable = false)
	private Integer timeInMinutes;
	
	@JoinTable(
			name = "EXAM_questions",
			joinColumns = @JoinColumn(name = "exam_id", referencedColumnName = "questions_id")
	)
	@ManyToMany
	private List<Question> questions;
	
	{
		
		questions = new ArrayList<>();
		
	}
	
	public Exam(Long id, String name, Double passingScore, Integer timeInMinutes) {
		
		this.id = id;
		this.name = name;
		this.passingScore = passingScore;
		this.timeInMinutes = timeInMinutes;
		
	}
	
	public Exam() {  }
	
	public Long getId() {
		
		return id;
		
	}
	
	public void setId(Long id) {
		
		this.id = id;
		
	}
	
	public String getName() {
		
		return name;
		
	}
	
	public void setName(String name) {
		
		this.name = name;
		
	}
	
	public Double getPassingScore() {
		
		return passingScore;
		
	}
	
	public void setPassingScore(Double passingScore) {
		
		this.passingScore = passingScore;
		
	}
	
	public Integer getTimeInMinutes() {
		
		return timeInMinutes;
		
	}
	
	public void setTimeInMinutes(Integer timeInMinutes) {
		
		this.timeInMinutes = timeInMinutes;
		
	}
	
	public List<Question> getQuestions() {
		
		return questions;
		
	}
	
	public void setQuestions(List<Question> questions) {
		
		this.questions = questions;
		
	}
	
}
