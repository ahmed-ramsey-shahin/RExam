package com.ramsey.rexam.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "ANSWER")
public class Answer implements Serializable {
	
	private static final long serialVersionUID = 5706383089070285644L;
	
	@Id
	@SequenceGenerator(
			name = "answerSequenceGenerator",
			allocationSize = 1,
			sequenceName = "answerSequenceGenerator"
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "answerSequenceGenerator"
	)
	@Column(updatable = false)
	private Long id;
	
	@Column(nullable = false)
	private String text;
	
	public Answer(Long id, String text) {
		
		this.id = id;
		this.text = text;
		
	}
	
	public Answer() {  }
	
	public Long getId() {
		
		return id;
		
	}
	
	public void setId(Long id) {
		
		this.id = id;
		
	}
	
	public String getText() {
		
		return text;
		
	}
	
	public void setText(String text) {
		
		this.text = text;
		
	}
	
}
