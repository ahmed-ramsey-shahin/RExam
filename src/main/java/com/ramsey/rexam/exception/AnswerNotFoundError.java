package com.ramsey.rexam.exception;

import java.io.Serial;

public class AnswerNotFoundError extends Error {
	
	@Serial
	private static final long serialVersionUID = -9009216243333718325L;
	
	public AnswerNotFoundError(Long answerId) {
		
		super(String.format("Answer %s, doesn't exist", answerId));
		
	}
	
	public AnswerNotFoundError(String answer) {
		
		super(String.format("Answer %s, doesn't exist", answer));
		
	}
	
}
