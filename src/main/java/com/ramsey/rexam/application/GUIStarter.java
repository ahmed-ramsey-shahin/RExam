package com.ramsey.rexam.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class GUIStarter extends Application {
	
	public void start() {
		
		launch();
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(
				Objects.requireNonNull(getClass().getClassLoader().getResource("MainScreen.fxml"))
		);
		stage.setTitle("Exam Selection");
		stage.setScene(new Scene(root));
		stage.centerOnScreen();
		stage.show();
		
	}
	
}
