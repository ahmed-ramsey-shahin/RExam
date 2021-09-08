package com.ramsey.rexam.view;

import com.ramsey.rexam.entity.Exam;
import com.ramsey.rexam.entity.Question;
import com.ramsey.rexam.view.util.*;
import com.ramsey.rexam.view.util.LinkedList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

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
	@FXML
	public Button previousButton;
	@FXML
	public Button nextButton;
	private String studentName;
	private Exam exam;
	private LinkedList<Pair<Node, QuestionPanelController>> questionPanels;
	private LinkedList<Pair<Node, QuestionPanelController>>.Node currentNode;
	private MediaPlayer player;
	
	public void init(String studentName, Exam exam) {
		
		questionPanels = new LinkedList<>();
		this.studentName = studentName;
		this.exam = exam;
		testNameText.setText(String.format("%s Test", exam.getName()));
		List<Question> questions = exam.getQuestions();
		
		questions.forEach(question -> {
			
			try {
				
				var result = QuestionPanelGenerator.createQuestionPanel();
				result.getValue().questionText.setText(question.getQuestion());
				ToggleGroup toggleGroup = new ToggleGroup();
				
				question.getAnswers().forEach(answer -> {
					
					CustomRadioButton<Long> radioButton = new CustomRadioButton<>();
					radioButton.setText(answer.getText());
					radioButton.setValue(answer.getId());
					radioButton.setToggleGroup(toggleGroup);
					radioButton.setStyle("-fx-padding: 15px; -fx-font-size: 15px;");
					result.getValue().answersVBox.getChildren().add(radioButton);
					
				});
				
				questionPanels.insert(result);
				
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
		
		currentNode = questionPanels.getHead();
		questionScrollPane.setContent(currentNode.getData().getKey());
		previousButton.setDisable(currentNode.getPrevious() == null);
		nextButton.setDisable(currentNode.getNext() == null);
		startTimer();
		
	}
	
	public void previousButtonClicked() {
	
		currentNode = currentNode.getPrevious();
		questionScrollPane.setContent(currentNode.getData().getKey());
		previousButton.setDisable(currentNode.getPrevious() == null);
		nextButton.setDisable(currentNode.getNext() == null);
		
	}
	
	public void nextButtonClicked() {
		
		currentNode = currentNode.getNext();
		questionScrollPane.setContent(currentNode.getData().getKey());
		previousButton.setDisable(currentNode.getPrevious() == null);
		nextButton.setDisable(currentNode.getNext() == null);
		
	}
	
	public void finishMouseClicked() {
	
		stopTheTest();
		
	}
	
	private void startTimer() {
		
		Thread timerThread = new Thread(
				new SecondsCounter(exam.getTimeInMinutes() * 60L, timerText, this)
		);
		timerThread.setDaemon(true);
		timerThread.start();
		
	}
	
	public void playAlertSound() {
		
		new Thread(() -> {
			
			Media beepBeep = null;
			
			try {
				
				beepBeep = new Media(
						Objects.requireNonNull(
								getClass().getClassLoader().getResource("beepbeep.wav")
						).toURI().toString()
				);
				
			} catch(URISyntaxException ignored) {  }
			
			assert beepBeep != null;
			player = new MediaPlayer(beepBeep);
			player.play();
			player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
			
		}).start();
		
	}
	
	public void stopTheTest() {
		
		if(player != null) {
			
			player.stop();
			
		}
		
	}
	
}
