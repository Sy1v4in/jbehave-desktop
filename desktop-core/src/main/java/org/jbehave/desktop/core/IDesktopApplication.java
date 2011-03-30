package org.jbehave.desktop.core;

/**
 * Interface that contain the general methods that every desktop test
 * application should have independently of test framework is being used.
 * 
 * @author Cristiano Gavião
 * 
 */
public interface IDesktopApplication {

	void init();

	void start();

	void restart();

	void shutdown();

}
