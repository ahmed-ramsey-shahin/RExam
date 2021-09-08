package com.ramsey.rexam.view;

import com.ramsey.rexam.beans.ExamBean;
import com.ramsey.rexam.entity.Exam;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class TestInfoScreenController {
	
	@FXML
	public TextField passingScoreTextField;
	@FXML
	public TextField examTimeTextField;
	@FXML
	public Text examNameText;
	private String studentName;
	private Exam exam;
	private ExamBean examBean;
	
	public void init(String studentName, Long examId) {
		
		this.studentName = studentName;
		examBean = ExamBean.getInstance();
		exam = examBean.getExam(examId);
		examNameText.setText(String.format("%s Test", exam.getName()));
		passingScoreTextField.setText(String.format("%.0f%%", exam.getPassingScore()));
		examTimeTextField.setText(String.format("%d minutes", exam.getTimeInMinutes()));
		
	}
	
	public void continueButtonClicked() {
		
		//
		
	}
	
}
