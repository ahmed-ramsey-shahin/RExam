package com.ramsey.rexam.view.util;

import com.ramsey.rexam.view.TestScreenController;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.text.NumberFormat;

public class SecondsCounter implements Runnable {
	
	private Long seconds;
	private Boolean shutdown;
	private final Text timerText;
	private final NumberFormat format;
	private final TestScreenController controller;
	
	public SecondsCounter(Long totalSeconds, Text timerText, TestScreenController controller) {
		
		shutdown = false;
		this.timerText = timerText;
		format = NumberFormat.getNumberInstance();
		format.setMinimumIntegerDigits(2);
		seconds = totalSeconds;
		this.controller = controller;
		
	}
	
	@Override
	public void run() {
		
		boolean triggered = false;
		
		while(!shutdown) {
			
			try {
				
				Thread.sleep(1000);
				
			} catch(InterruptedException ignored) {  }
			
			seconds--;
			long temp = seconds;
			int hours = (int) (seconds - (seconds % 3600));
			temp %= 3600;
			int minutes = (int) (seconds - (seconds % 60));
			temp %= 60;
			timerText.setText(String.format(
					"%s:%s:%s",
					format.format(hours / 3600),
					format.format(minutes / 60),
					format.format(temp)
			));
			
			if(seconds.compareTo(300L) < 0 && !triggered) {
				
				controller.playAlertSound();
				timerText.setFill(Color.RED);
				triggered = true;
				
			}
			
			if(seconds.equals(0L)) {
				
				shutdown = true;
				
			}
			
		}
		
		Platform.runLater(controller::stopTheTest);
		
	}
	
}
