package com.ramsey.rexam.exception;

public class ExamNotFoundError extends Error {
	
	private static final long serialVersionUID = -1296971287593579231L;
	
	public ExamNotFoundError(Long examId) {
		
		super(String.format("Exam %d, doesn't exist", examId));
		
	}
	
	public ExamNotFoundError(String examName) {
		
		super(String.format("Exam %s, doesn't exist", examName));
		
	}
	
}
