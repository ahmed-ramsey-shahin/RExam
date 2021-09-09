package com.ramsey.rexam.view;

import com.ramsey.rexam.entity.Question;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class QuestionPanelController {
	
	@FXML
	public Text questionText;
	@FXML
	public VBox answersVBox;
	public Question question;
	
}
