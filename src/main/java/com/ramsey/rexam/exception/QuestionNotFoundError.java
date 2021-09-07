package com.ramsey.rexam.exception;

public class QuestionNotFoundError extends Error {
	
	private static final long serialVersionUID = -2734617226839864678L;
	
	public QuestionNotFoundError(Long questionId) {
		
		super(String.format("Question %d, doesn't exist", questionId));
		
	}
	
	public QuestionNotFoundError(String question) {
		
		super(String.format("Question %s, doesn't exist", question));
		
	}
	
}
