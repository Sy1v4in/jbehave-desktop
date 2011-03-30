package desktop.example.calculator.test.wraper;

import org.jbehave.desktop.swing.AbstractSwingApplication;

import desktop.example.calculator.CalculatorPanel;

/**
 * This concrete class extends the AbstractSwingApplication and
 * wrapper the Calculator main class.
 * 
 * @author Cristiano Gavião
 * 
 */
public class CalculatorWraperApplication extends AbstractSwingApplication {

	public CalculatorWraperApplication() {
		super(CalculatorPanel.class, new String[0]);
	}

}
