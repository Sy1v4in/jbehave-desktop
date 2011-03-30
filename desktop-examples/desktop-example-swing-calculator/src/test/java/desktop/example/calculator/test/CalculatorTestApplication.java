package desktop.example.calculator.test;

import org.jbehave.desktop.swing.AbstractSwingApplication;

import desktop.example.calculator.CalculatorPanel;

/**
 * This concrete class extends the AbstractSwingApplication and
 * wrapper the Calculator main class.
 * 
 * @author Cristiano Gavião
 * 
 */
public class CalculatorTestApplication extends AbstractSwingApplication {

	public CalculatorTestApplication() {
		super(CalculatorPanel.class, new String[0]);
	}

}
