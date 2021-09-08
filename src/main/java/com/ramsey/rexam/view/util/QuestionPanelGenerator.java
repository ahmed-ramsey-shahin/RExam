package com.ramsey.rexam.view.util;

import com.ramsey.rexam.view.QuestionPanelController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.util.Objects;

public class QuestionPanelGenerator {
	
	public static Pair<Node, QuestionPanelController> createQuestionPanel() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(
				Objects.requireNonNull(
						QuestionPanelGenerator.class.getClassLoader().getResource("QuestionPanel.fxml")
				)
		);
		Node node = loader.load();
		QuestionPanelController nodeController = loader.getController();
		return new Pair<>(node, nodeController);
		
	}
	
}
