package com.ramsey.rexam.view.util;

import javafx.scene.control.RadioButton;

public class CustomRadioButton<T> extends RadioButton {
	
	private T value;
	
	public T getValue() {
		
		return value;
		
	}
	
	public void setValue(T value) {
		
		this.value = value;
		
	}
	
}
