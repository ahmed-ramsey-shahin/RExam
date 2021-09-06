package com.ramsey.rexam.main;

import com.ramsey.rexam.application.StartupService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class RExam {
	
	public static void main(String[] args) {
		
		Weld weld = new Weld();
		
		try(WeldContainer weldContainer = weld.initialize()) {
		
			weldContainer.select(StartupService.class).get().start();
		
		}
		
	}
	
}
