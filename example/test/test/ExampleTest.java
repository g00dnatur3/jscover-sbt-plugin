package test;

import org.junit.Test;

import play.test.FluentTestWithCoverage;

public class ExampleTest extends FluentTestWithCoverage {

	@Test
	public void indexPageTest() {
		goTo(getBaseUrl()).await().untilPage();
	}
	
}
