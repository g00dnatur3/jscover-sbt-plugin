package play.test;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;
import static play.test.Helpers.testServer;

import java.io.File;
import java.io.FileWriter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.fluentlenium.core.FluentAdapter;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FluentTestWithCoverage extends FluentAdapter {
	
	protected TestServer testServer;
	
	protected WebDriver webDriver;
	
	protected int port = 3333;
	
	protected String baseUrl = "http://localhost:" + port;;
	
    @Rule
    public TestRule watchman = new TestWatcher() {
        @Override
        public void starting(Description description) {
            super.starting(description);
        	testServer = testServer(port, fakeApplication(createTestSettings()));
        	start(testServer);
            initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
            initTest();
        }

        @Override
        public void finished(Description description) {
            super.finished(description);
            stop(testServer);
            try {
				String json = (String) ((JavascriptExecutor) webDriver)
						.executeScript("return jscoverage_serializeCoverageToJSON();");
				File f = new File(CoverageTestSettings.REPORTS_DIR 
						+ "/" + description.getClassName() 
						+ "$" + description.getMethodName() + "/");
				if (f.exists()) {
					FileUtils.deleteDirectory(f);
				}
				f.mkdir();
				FileWriter fw = new FileWriter(new File(f, "jscoverage.json"));
				IOUtils.write(json, fw);
				fw.close();
            } catch (Exception e) {
            	e.printStackTrace();
            }
            quit();
        }
    };
	
    @Override
    public String getDefaultBaseUrl() {
        return baseUrl;
    }
	
    @Override
    public WebDriver getDefaultDriver() {
    	if (webDriver == null) {
    		webDriver = play.api.test.WebDriverFactory.apply(FirefoxDriver.class);
    	}
    	return webDriver;
    }
	
	public CoverageTestSettings createTestSettings() {
		return new CoverageTestSettings(true);
	}
	
}
