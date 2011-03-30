package org.jbehave.desktop.swing;

import org.jbehave.desktop.core.IDesktopApplication;
import org.uispec4j.UISpec4J;
import org.uispec4j.UISpecAdapter;
import org.uispec4j.Window;
import org.uispec4j.assertion.Assertion;
import org.uispec4j.assertion.UISpecAssert;
import org.uispec4j.interception.MainClassAdapter;
import org.uispec4j.interception.toolkit.UISpecDisplay;


/**
 * Abstract class that do the integration with UISpec4j.
 * 
 * @author Cristiano Gavião
 * 
 */
public class AbstractSwingApplication implements IDesktopApplication {

	static class AdapterNotFoundException extends RuntimeException {
		private static final long serialVersionUID = -9020398360682437273L;

		public AdapterNotFoundException() {
			super();
		}

		public AdapterNotFoundException(String adapterClassName, Exception e) {
			super("Adapter class '" + adapterClassName + "' not found", e);
		}
	}

	private Class<?> mainApplicationClass;
	private UISpecAdapter adapter;
	private String[] args;

	public AbstractSwingApplication(Class<?> mainApplicationClass,
			String... args) {
		this.mainApplicationClass = mainApplicationClass;
		this.args = args;
	}

	/**
	 * Returns the intersection of two assertions.
	 * 
	 * @see UISpecAssert#and(Assertion[])
	 */
	public Assertion and(Assertion... assertions) {
		return UISpecAssert.and(assertions);
	}

	/**
	 * Checks that the given assertion equals the expected parameter.
	 * 
	 * @see UISpecAssert#assertEquals(boolean,Assertion)
	 */
	public void assertEquals(boolean expected, Assertion assertion) {
		UISpecAssert.assertEquals(expected, assertion);
	}

	/**
	 * Checks that the given assertion equals the expected parameter. If it
	 * fails an AssertionFailedError is thrown with the given message.
	 * 
	 * @see UISpecAssert#assertEquals(String,boolean,Assertion)
	 */
	public void assertEquals(String message, boolean expected,
			Assertion assertion) {
		UISpecAssert.assertEquals(message, expected, assertion);
	}

	/**
	 * Checks that the given assertion fails.
	 * 
	 * @see UISpecAssert#assertFalse(Assertion)
	 */
	public void assertFalse(Assertion assertion) {
		UISpecAssert.assertFalse(assertion);
	}

	/**
	 * Checks that the given assertion fails. If it succeeds an
	 * AssertionFailedError is thrown with the given message.
	 * 
	 * @see UISpecAssert#assertFalse(String,Assertion)
	 */
	public void assertFalse(String message, Assertion assertion) {
		UISpecAssert.assertFalse(message, assertion);
	}

	/**
	 * Checks the given assertion. This method is equivalent to {@link
	 * #assertTrue(Assertion}.
	 * 
	 * @see UISpecAssert#assertThat(Assertion)
	 */
	public void assertThat(Assertion assertion) {
		UISpecAssert.assertThat(assertion);
	}

	/**
	 * Checks the given assertion. If it fails an AssertionFailedError is thrown
	 * with the given message. This method is equivalent to {@link
	 * #assertTrue(String,Assertion}.
	 * 
	 * @see UISpecAssert#assertTrue(String,Assertion)
	 */
	public void assertThat(String message, Assertion assertion) {
		UISpecAssert.assertThat(message, assertion);
	}

	/**
	 * Checks the given assertion. This method is equivalent to {@link
	 * #assertThat(Assertion}.
	 * 
	 * @see UISpecAssert#assertTrue(Assertion)
	 */
	public void assertTrue(Assertion assertion) {
		UISpecAssert.assertTrue(assertion);
	}

	/**
	 * Checks the given assertion. If it fails an AssertionFailedError is thrown
	 * with the given message. This method is equivalent to {@link
	 * #assertThat(String,Assertion}.
	 * 
	 * @see UISpecAssert#assertTrue(String,Assertion)
	 */
	public void assertTrue(String message, Assertion assertion) {
		UISpecAssert.assertTrue(message, assertion);
	}

	protected UISpecAdapter getAdapter() throws AdapterNotFoundException {
		if (adapter == null) {
			throw new AdapterNotFoundException();
		}
		return adapter;
	}

	/**
	 * Returns the Window created by the adapter.
	 * 
	 * @throws AdapterNotFoundException
	 *             if the <code>uispec4j.adapter</code> property does not refer
	 *             to a valid adapter
	 */
	public Window getMainWindow() throws AdapterNotFoundException {
		return getAdapter().getMainWindow();
	}

	public void init() {

		UISpec4J.init();

		setAdapter(new MainClassAdapter(mainApplicationClass, args));

		UISpecDisplay.instance().reset();
	}

	/**
	 * Returns a negation of the given assertion.
	 * 
	 * @see UISpecAssert#not(Assertion)
	 */
	public Assertion not(Assertion assertion) {
		return UISpecAssert.not(assertion);
	}

	/**
	 * Returns the union of two assertions.
	 * 
	 * @see UISpecAssert#or(Assertion[])
	 */
	public Assertion or(Assertion... assertions) {
		return UISpecAssert.or(assertions);
	}

	public void restart() {
		UISpecDisplay.instance().reset();
	}

	protected void setAdapter(UISpecAdapter adapter) {
		this.adapter = adapter;
	}

	public void shutdown() {
		UISpecDisplay.instance().rethrowIfNeeded();
		UISpecDisplay.instance().reset();
	}

	@Override
	public void start() {
		UISpecDisplay.instance().reset();
	}

	/**
	 * Waits for at most 'waitTimeLimit' ms until the assertion is true.
	 * 
	 * @see UISpecAssert#waitUntil(Assertion, long)
	 */
	public void waitUntil(Assertion assertion, long waitTimeLimit) {
		UISpecAssert.waitUntil(assertion, waitTimeLimit);
	}

	/**
	 * Waits for at most 'waitTimeLimit' ms until the assertion is true. If it
	 * fails an AssertionFailedError is thrown with the given message.
	 * 
	 * @see UISpecAssert#waitUntil(String,Assertion,long)
	 */
	public void waitUntil(String message, Assertion assertion,
			long waitTimeLimit) {
		UISpecAssert.waitUntil(message, assertion, waitTimeLimit);
	}

}
