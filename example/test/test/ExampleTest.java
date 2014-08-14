package test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import play.test.FluentTestWithCoverage;

public class ExampleTest extends FluentTestWithCoverage {

	@Test
	public void indexPageTest() {
		goTo(getBaseUrl()).await().atMost(5, TimeUnit.SECONDS).until(".landing-page").isPresent();
		
		$("#nextPage").click();

		await().atMost(5, TimeUnit.SECONDS).until(".page1").isPresent();
		
		$("#prevPage").click();
		
		await().atMost(5, TimeUnit.SECONDS).until(".landing-page").isPresent();
	}
	
}
