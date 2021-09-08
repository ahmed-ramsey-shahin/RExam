package com.ramsey.rexam.view;

import com.ramsey.rexam.beans.ExamBean;
import com.ramsey.rexam.exception.ExamNotFoundError;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
	
	@FXML
	public TextField studentNameTextField;
	@FXML
	public ComboBox<Pair<String, Long>> examComboBox;
	@FXML
	public TextField filterTextField;
	@FXML
	public Button startButton;
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
	
	public void filterTextFieldKeyReleased(KeyEvent keyEvent) {
		
		String filterCriteria = filterTextField.getText();
		examComboBox.getItems().clear();
		
		try {
			
			examBean.getExams(filterCriteria)
					.forEach(exam -> examComboBox.getItems().add(new Pair<>(exam.getName(), exam.getId())));
			
		} catch(ExamNotFoundError ex) {
			
			warningText.setText("The exam you're looking for was not found");
			
		}
		
	}
	
	public void startButtonMouseClicked(MouseEvent mouseEvent) {
		
		//
		
	}
	
}
