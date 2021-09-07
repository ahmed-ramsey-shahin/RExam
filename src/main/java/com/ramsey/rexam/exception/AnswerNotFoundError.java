package com.ramsey.rexam.exception;

public class AnswerNotFoundError extends Error {
	
	private static final long serialVersionUID = -9009216243333718325L;
	
	public AnswerNotFoundError(Long answerId) {
		
		super(String.format("Answer %s, doesn't exist", answerId));
		
	}
	
	public AnswerNotFoundError(String answer) {
		
		super(String.format("Answer %s, doesn't exist", answer));
		
	}
	
}
