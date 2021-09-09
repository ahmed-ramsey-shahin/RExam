package com.ramsey.rexam.view;

import com.ramsey.rexam.entity.Answer;
import com.ramsey.rexam.entity.Exam;
import com.ramsey.rexam.entity.Question;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class ResultScreenController {
	
	private HashMap<Question, Answer> answers;
	private HashMap<Question, Boolean> markedForReview;
	
	@FXML
	public Button reviewMarkedQuestionsButton;
	@FXML
	public TextField studentNameTextField;
	@FXML
	public TextField testNameTextField;
	@FXML
	public TextField totalNumberOfQuestionsTextField;
	@FXML
	public TextField numberOfSolvedQuestionsTextField;
	@FXML
	public TextField requiredScoreTextField;
	@FXML
	public TextField achievedScoreTextField;
	@FXML
	public TextField numberOfCorrectAnswersTextField;
	@FXML
	public Text statusText;
	
	public void init(
			String studentName,
			Exam exam,
			HashMap<Question, Answer> answers,
			HashMap<Question, Boolean> markedForReview
	) {
		
		this.answers = answers;
		this.markedForReview = markedForReview;
		int numberOfCorrectAnswers = (int) answers.keySet().stream()
				.filter(key -> answers.get(key).getId().equals(key.getRightAnswer().getId())).count();
		double score = (double) (numberOfCorrectAnswers) * 100.0 / (double) (exam.getQuestions().size());
		
		studentNameTextField.setText(studentName);
		testNameTextField.setText(exam.getName());
		totalNumberOfQuestionsTextField.setText(String.valueOf(exam.getQuestions().size()));
		numberOfSolvedQuestionsTextField.setText(String.valueOf(answers.size()));
		requiredScoreTextField.setText(String.format("%.1f%%", exam.getPassingScore()));
		numberOfCorrectAnswersTextField.setText(String.valueOf(numberOfCorrectAnswers));
		reviewMarkedQuestionsButton.setDisable(
				markedForReview.keySet().stream().allMatch(key -> markedForReview.get(key).equals(false))
		);
		achievedScoreTextField.setText(String.format(
				"%.1f%%",
				score
		));
		
		if(score > exam.getPassingScore()) {
			
			statusText.setText("Successful");
			statusText.setFill(Color.GREEN);
			
		} else {
			
			statusText.setText("Fail");
			statusText.setFill(Color.RED);
			
		}
		
	}
	
	@FXML
	public void onExitButtonClicked() {
		
		System.exit(0);
		
	}
	
	@FXML
	public void onReviewMarkedQuestionsButtonClicked() {
		
		markedForReview.keySet().removeIf(key -> markedForReview.get(key).equals(false));
		answers.keySet().removeIf(key -> !markedForReview.containsKey(key));
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(
				getClass().getClassLoader().getResource("ReviewScreen.fxml")
		);
		
		try {
			
			root = loader.load();
			
		} catch(IOException ignored){  }
		
		Stage stage = new Stage();
		stage.setTitle("Review Marked Questions");
		assert root != null;
		stage.setScene(new Scene(root));
		stage.show();
		((ReviewScreenController) loader.getController()).init(answers);
		
	}
	
}
