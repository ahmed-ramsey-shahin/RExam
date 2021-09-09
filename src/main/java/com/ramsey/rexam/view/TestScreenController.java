package com.ramsey.rexam.view;

import com.ramsey.rexam.entity.Answer;
import com.ramsey.rexam.entity.Exam;
import com.ramsey.rexam.entity.Question;
import com.ramsey.rexam.view.util.*;
import com.ramsey.rexam.view.util.LinkedList;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.NumberFormat;
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
	private Image check;
	private Image close;
	HashMap<Question, Answer> answers;
	HashMap<Question, ImageView> imageViews;
	
	public void init(String studentName, Exam exam) {
		
		questionPanels = new LinkedList<>();
		this.studentName = studentName;
		this.exam = exam;
		testNameText.setText(String.format("%s Test", exam.getName()));
		List<Question> questions = exam.getQuestions();
		Collections.shuffle(questions);
		answers = new HashMap<>();
		imageViews = new HashMap<>();
		check = new Image(
				Objects.requireNonNull(
						getClass().getClassLoader().getResourceAsStream("check.png")
				)
		);
		close = new Image(
				Objects.requireNonNull(
						getClass().getClassLoader().getResourceAsStream("close.png")
				)
		);
		
		questions.forEach(question -> {
			
			Collections.shuffle(question.getAnswers());
			
			try {
				
				var result = QuestionPanelGenerator.createQuestionPanel();
				result.getValue().questionText.setText(question.getQuestion());
				result.getValue().question = question;
				ToggleGroup toggleGroup = new ToggleGroup();
				
				question.getAnswers().forEach(answer -> {
					
					CustomRadioButton<Pair<Question, Answer>> radioButton = new CustomRadioButton<>();
					radioButton.setText(answer.getText());
					radioButton.setValue(new Pair<>(question, answer));
					radioButton.setToggleGroup(toggleGroup);
					radioButton.setStyle("-fx-padding: 15px; -fx-font-size: 15px;");
					radioButton.selectedProperty().addListener(
							(
									ObservableValue<? extends Boolean> obs,
									Boolean wasPreviouslySelected,
									Boolean isNowSelected
							) -> {
								
								handleSelectAnswerEvent(radioButton.getValue());
								finishButton.setDisable(!canFinish());
								
							}
					);
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
		refreshButtons();
		startTimer();
		initializeTotalQuestions();
		
	}
	
	public void previousButtonClicked() {
	
		currentNode = currentNode.getPrevious();
		refreshButtons();
		
	}
	
	public void nextButtonClicked() {
		
		currentNode = currentNode.getNext();
		refreshButtons();
		
	}
	
	public void finishMouseClicked() {
	
		stopTheTest();
		
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
	
	private void refreshButtons() {
		
		questionScrollPane.setContent(currentNode.getData().getKey());
		previousButton.setDisable(currentNode.getPrevious() == null);
		nextButton.setDisable(currentNode.getNext() == null);
		finishButton.setDisable(!canFinish());
		
	}
	
	private void startTimer() {
		
		Thread timerThread = new Thread(
				new SecondsCounter(exam.getTimeInMinutes() * 60L, timerText, this)
		);
		timerThread.setDaemon(true);
		timerThread.start();
		
	}
	
	private Boolean canFinish() {
		
		return answers.keySet().size() == questionPanels.size();
		
	}
	
	private void refreshTotalQuestions() {
		
		imageViews.get(currentNode.getData().getValue().question).setImage(check);
		
	}
	
	private void initializeTotalQuestions() {
		
		VBox vBox = (VBox) questionsScrollPane.getContent();
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMinimumIntegerDigits(questionPanels.size().toString().length());
		var question = questionPanels.getHead();

		while(question != null) {
			
			HBox hBox = new HBox();
			hBox.setSpacing(20);
			hBox.setAlignment(Pos.CENTER);
			hBox.autosize();
			vBox.getChildren().add(hBox);
			
			for(int j = 0; j <= 3; ++j) {

				if(question != null) {
					
					ImageView child = new ImageView(close);
					imageViews.put(question.getData().getValue().question, child);
					child.setFitHeight(24);
					child.setFitWidth(24);
					hBox.getChildren().add(child);
					question = question.getNext();

				}

			}

		}
		
	}
	
	private void handleSelectAnswerEvent(Pair<Question, Answer> eventValue) {
		
		answers.put(eventValue.getKey(), eventValue.getValue());
		refreshTotalQuestions();
		
	}
	
}
