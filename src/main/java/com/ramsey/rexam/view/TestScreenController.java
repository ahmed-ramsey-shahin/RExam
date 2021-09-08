package com.ramsey.rexam.view;

import com.ramsey.rexam.entity.Exam;
import com.ramsey.rexam.entity.Question;
import com.ramsey.rexam.view.util.CustomRadioButton;
import com.ramsey.rexam.view.util.Pair;
import com.ramsey.rexam.view.util.QuestionPanelGenerator;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
	private LinkedList<Pair<Node, QuestionPanelController>> questionPanels;
	
	public void init(String studentName, Exam exam) {
		
		questionPanels = new LinkedList<>();
		this.studentName = studentName;
		this.exam = exam;
		testNameText.setText(String.format("%s Test", exam.getName()));
		List<Question> questions = exam.getQuestions();
		
		questions.forEach(question -> {
			
			try {
				
				var result = QuestionPanelGenerator.createQuestionPanel();
				result.getValue().questionNumberText.setText(
						String.format("Question Number: %d", questionPanels.size() + 1)
				);
				result.getValue().questionText.setText(question.getQuestion());
				ToggleGroup toggleGroup = new ToggleGroup();
				question.getAnswers().forEach(answer -> {
					
					CustomRadioButton<Long> radioButton = new CustomRadioButton<>();
					radioButton.setText(answer.getText());
					radioButton.setValue(answer.getId());
					radioButton.setToggleGroup(toggleGroup);
					result.getValue().answersVBox.getChildren().add(radioButton);
					
				});
				questionPanels.add(result);
				
			} catch(IOException e) {
				
				JOptionPane.showMessageDialog(
						null,
						"There was an error loading a question, the program will exit.",
						"Error",
						JOptionPane.ERROR_MESSAGE
				);
				System.exit(0);
				
			}
			
		});
		questionScrollPane.setContent(questionPanels.getFirst().getKey());
		
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
