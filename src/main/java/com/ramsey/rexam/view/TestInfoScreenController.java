package com.ramsey.rexam.view;

import com.ramsey.rexam.beans.ExamBean;
import com.ramsey.rexam.entity.Exam;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TestInfoScreenController {
	
	@FXML
	public TextField passingScoreTextField;
	@FXML
	public TextField examTimeTextField;
	@FXML
	public Text examNameText;
	private String studentName;
	private Exam exam;
	
	public void init(String studentName, Long examId) {
		
		this.studentName = studentName;
		ExamBean examBean = ExamBean.getInstance();
		exam = examBean.getExam(examId);
		examNameText.setText(String.format("%s Test", exam.getName()));
		passingScoreTextField.setText(String.format("%.0f%%", exam.getPassingScore()));
		examTimeTextField.setText(String.format("%d minutes", exam.getTimeInMinutes()));
		
	}
	
	public void continueButtonClicked() throws IOException {
		
		Stage stage = (Stage) examNameText.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(
				Objects.requireNonNull(getClass().getClassLoader().getResource("TestScreen.fxml"))
		);
		Scene scene = new Scene(loader.load());
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setTitle(String.format("%s Test", exam.getName()));
		stage.show();
		((TestScreenController) loader.getController()).init(studentName, exam);
		
	}
	
}
