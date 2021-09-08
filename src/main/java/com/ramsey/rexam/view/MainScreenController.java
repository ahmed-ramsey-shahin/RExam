package com.ramsey.rexam.view;

import com.ramsey.rexam.beans.ExamBean;
import com.ramsey.rexam.exception.ExamNotFoundError;
import com.ramsey.rexam.view.util.Pair;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
	
	@FXML
	public TextField studentNameTextField;
	@FXML
	public ComboBox<Pair<String, Long>> examComboBox;
	@FXML
	public TextField filterTextField;
	@FXML
	public Label warningText;
	private ExamBean examBean;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		warningText.setText("");
		examBean = ExamBean.getInstance();
		examComboBox.getItems().clear();
		examBean.getExams()
				.forEach(exam -> examComboBox.getItems().add(new Pair<>(exam.getName(), exam.getId())));
 	
	}
	
	public void filterTextFieldKeyReleased() {
		
		String filterCriteria = filterTextField.getText();
		examComboBox.getItems().clear();
		
		try {
			
			examBean.getExams(filterCriteria)
					.forEach(exam -> examComboBox.getItems().add(new Pair<>(exam.getName(), exam.getId())));
			
		} catch(ExamNotFoundError ex) {
			
			warningText.setText("The exam you're looking for was not found");
			
		}
		
	}
	
	public void startButtonMouseClicked() throws IOException {
		
		String studentName = studentNameTextField.getText();
		Pair<String, Long> selectedExam = examComboBox.getSelectionModel().getSelectedItem();
		
		if(studentName.trim().equals("")) {
			
			warningText.setText("Student name must not be empty");
			return;
			
		}
		
		if(selectedExam == null) {
			
			warningText.setText("Select an exam first");
			return;
			
		}
		
		Stage stage = (Stage) filterTextField.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(
				Objects.requireNonNull(getClass().getClassLoader().getResource("TestInfoScreen.fxml"))
		);
		Scene scene = new Scene(loader.load());
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setTitle("Test Info");
		stage.show();
		((TestInfoScreenController) loader.getController()).init(studentName, selectedExam.getValue());
		
	}
	
}
