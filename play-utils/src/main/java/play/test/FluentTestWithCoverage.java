package play.test;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;
import static play.test.Helpers.testServer;

import java.io.File;
import java.io.FileWriter;

import org.apache.commons.io.IOUtils;
import org.fluentlenium.core.FluentAdapter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import play.test.TestServer;

public class FluentTestWithCoverage extends FluentAdapter {
	
	protected static TestServer testServer;
	
	protected static String className;
	
	protected static WebDriver webDriver;
	
	protected static int port = 3333;
	
	protected static String baseUrl;
	
	public FluentTestWithCoverage() {
		className = getClass().getSimpleName();
	}
	
    @Rule
    public TestRule watchman = new TestWatcher() {
        @Override
        public void starting(Description description) {
            super.starting(description);
            initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
            initTest();
        }

        @Override
        public void finished(Description description) {
            super.finished(description);
            try {
				String json = (String) ((JavascriptExecutor) webDriver)
						.executeScript("return jscoverage_serializeCoverageToJSON();");
				File f = new File(CoverageTestSettings.REPORTS_DIR + "/" + className + "/");
				if (f.exists()) {
					f.delete();
				} else {
					f.mkdir();
				}
				FileWriter fw = new FileWriter(new File(f, "jscoverage.json"));
				IOUtils.write(json, fw);
				fw.close();
            } catch (Exception e) {
            	e.printStackTrace();
            }
            quit();
        }
    };
    
	@BeforeClass
    public static void startApp() throws Exception {
		baseUrl = "http://localhost:" + port;
    	testServer = testServer(port, fakeApplication(new CoverageTestSettings(true)));
    	start(testServer);
    }
	
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

	@AfterClass
	public static void stopApp() throws Exception {
		stop(testServer);
		webDriver = null; //very important -> getDefaultDriver()
	}

}
