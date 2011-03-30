package desktop.example.calculator.test.runners;

import static org.jbehave.core.io.CodeLocations.codeLocationFromPath;

import java.util.List;

import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.AnnotatedEmbedderRunner;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.ParameterConverters;
import org.junit.Test;
import org.junit.runner.RunWith;

import desktop.example.calculator.test.runners.CalculatorAnnotatedEmbedderWithSteps.MyReportBuilder;
import desktop.example.calculator.test.runners.CalculatorAnnotatedEmbedderWithSteps.MyStoryControls;
import desktop.example.calculator.test.runners.CalculatorAnnotatedEmbedderWithSteps.MyStoryLoader;
import desktop.example.calculator.test.steps.CalculatorSteps;

/**
 * Run stories via annotated embedder configuration and steps. Here we are only
 * concerned with using the container to compose the configuration and the steps
 * instances.
 * 
 * @author Cristiano Gavião
 * 
 */
@RunWith(AnnotatedEmbedderRunner.class)
@Configure(storyControls = MyStoryControls.class, storyLoader = MyStoryLoader.class, storyReporterBuilder = MyReportBuilder.class, parameterConverters = {
		ParameterConverters.EnumConverter.class,
		ParameterConverters.NumberConverter.class })
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true, ignoreFailureInStories = true, ignoreFailureInView = true)
@UsingSteps(instances = { CalculatorSteps.class })
public class CalculatorAnnotatedEmbedderWithSteps extends InjectableEmbedder {

	@Test
	public void run() {
		injectedEmbedder().runStoriesAsPaths(storyPaths());
	}

	protected List<String> storyPaths() {
		return new StoryFinder().findPaths(
				codeLocationFromPath("src/test/resources"), "**/*.story", "");
	}

	public static class MyStoryControls extends StoryControls {
		public MyStoryControls() {
			doDryRun(false);
			doSkipScenariosAfterFailure(false);
		}
	}

	public static class MyStoryLoader extends LoadFromClasspath {
		public MyStoryLoader() {
			super(CalculatorAnnotatedEmbedderWithSteps.class.getClassLoader());
		}
	}

	public static class MyReportBuilder extends StoryReporterBuilder {
		public MyReportBuilder() {
			this.withFormats(org.jbehave.core.reporters.Format.CONSOLE,
					org.jbehave.core.reporters.Format.TXT,
					org.jbehave.core.reporters.Format.HTML,
					org.jbehave.core.reporters.Format.XML).withDefaultFormats();
		}
	}
}
