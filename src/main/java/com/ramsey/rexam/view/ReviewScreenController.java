package com.ramsey.rexam.view;

import com.ramsey.rexam.entity.Answer;
import com.ramsey.rexam.entity.Question;
import com.ramsey.rexam.view.util.QuestionPanelGenerator;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;

public class ReviewScreenController {
	
	@FXML
	public VBox questionsVBox;
	
	public void init(HashMap<Question, Answer> answers) {
		
		answers.keySet().forEach(key -> {
			
			try {
				
				var result = QuestionPanelGenerator.createQuestionPanel();
				result.getValue().questionText.setText(key.getQuestion());
				result.getValue().answersVBox.setSpacing(20);
				
				key.getAnswers().forEach(answer -> {
					
					Text answerText = new Text(answer.getText());
					answerText.setStyle("-fx-font-size: 20px;");
					
					if(answer.getId().equals(answers.get(key).getId())) {
						
						answerText.setFill(Color.RED);
						
					}
					
					if(answer.getId().equals(key.getRightAnswer().getId())) {
						
						answerText.setFill(Color.GREEN);
						
					}
					
					result.getValue().answersVBox.getChildren().add(answerText);
					
				});
				
				questionsVBox.getChildren().add(result.getKey());
				
			} catch(IOException e) {
				
				e.printStackTrace();
				
			}
			
		});
		
	}
	
}
