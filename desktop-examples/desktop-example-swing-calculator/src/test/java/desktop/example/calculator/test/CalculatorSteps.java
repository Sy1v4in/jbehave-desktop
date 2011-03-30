package desktop.example.calculator.test;

import static org.uispec4j.assertion.UISpecAssert.assertTrue;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.uispec4j.Panel;

import desktop.example.calculator.CalculatorPanel;
import desktop.example.calculator.Operation;
import desktop.example.calculator.State;

/**
 * Steps class for the Calculator example.
 * 
 * @author Cristiano Gavião
 * 
 */
public class CalculatorSteps {

	private CalculatorTestApplication calculatorApp;
	private Panel calculatorPanel = null;

	@BeforeStories
	public void beforeStories() {
		calculatorApp = new CalculatorTestApplication();

		// Must be called before anything related to UI !!!
		calculatorApp.init();
	}

	@BeforeStory
	public void beforeStory() {
		calculatorApp.start();
	}

	@AfterStories
	public void afterStories() {
		calculatorApp.shutdown();
	}

	@Given("that the calculator is $state")
	public void givenThatCalculatorIsAtExpectedMode(State state) {

		calculatorPanel = calculatorApp.getMainWindow().getPanel(
				"calculator_panel");
		CalculatorPanel panel = (CalculatorPanel) calculatorPanel
				.getAwtContainer();
		calculatorPanel.getButton("C").click();

		// here I'm using the Hamcrest Assertion Library

		org.hamcrest.MatcherAssert.assertThat(panel.getCurrentState(),
				org.hamcrest.CoreMatchers.equalTo(state));
		assertTrue(calculatorPanel.getTextBox().textEquals("0"));
	}

	@When("user do the operation <operation> between <value1> and <value2>")
	public void whenUserEnterOperationOnTwoNumbers(
			@Named("operation") Operation operation,
			@Named("value1") String value1, @Named("value2") String value2) {

		enterNumberRecursively(value1);
		calculatorPanel.getButton(operation.getSymbol()).click();
		enterNumberRecursively(value2);
		calculatorPanel.getButton("=").click();

	}

	private void enterNumberRecursively(String value) {
		if (value.length() > 1) {
			char[] arraystr = value.toCharArray();
			for (char digit : arraystr) {
				calculatorPanel.getButton(String.valueOf(digit)).click();
			}
		} else {
			calculatorPanel.getButton(value).click();

		}
	}

	@Then("the result should be <result>")
	public void thenResultShouldBe(@Named("result") String result) {
		// here I'm using the UISpec4j Assertion Library
		assertTrue(calculatorPanel.getTextBox("result").textEquals(result));

	}
}
