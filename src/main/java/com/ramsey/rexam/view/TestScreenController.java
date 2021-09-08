package com.ramsey.rexam.view;

import com.ramsey.rexam.entity.Exam;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

public class TestScreenController {
	
	@FXML
	public Text timerText;
	@FXML
	public ScrollPane questionScrollPane;
	@FXML
	public Text testNameText;
	@FXML
	public Button finishButton;
	@FXML
	public ScrollPane questionsScrollPane;
	@FXML
	public CheckBox markForReviewCheckBox;
	private String studentName;
	private Exam exam;
	
	public void init(String studentName, Exam exam) {
		
		this.studentName = studentName;
		this.exam = exam;
		testNameText.setText(String.format("%s Test", exam.getName()));
		
	}
	
	public void previousButtonClicked() {
	
		//
		
	}
	
	public void nextButtonClicked() {
	
		//
		
	}
	
	public void finishMouseClicked() {
	
		//
		
	}
	
}
