jscover-sbt-plugin
==================

A plugin for sbt & play to enable getting javascript code coverage for functional style tests thru the use of JSCover library.

This plugin was developed with play 2.2.1, but should work for all 2.2.x versions.

Installation Guide
-------------------------

First, add the following to the `project/plugins.sbt` file:
```
resolvers += Resolver.url("sbt-plugins", url("http://dl.bintray.com/g00dnatur3/sbt-plugins"))(Resolver.ivyStylePatterns)

addSbtPlugin("g00dnatur3" % "jscover-sbt-plugin" % "1.0.02")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.3.2")
```
Next, add the following to the `build.sbt` file:
```
JSCoverPlugin.jscoverSettings

buildInfoSettings

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq[BuildInfoKey](jscoverSourcePath, jscoverDestinationPath, jscoverReportsDir)

buildInfoPackage := "sbt"

resolvers += "play-utils" at "http://dl.bintray.com/g00dnatur3/play-utils/"

libraryDependencies ++= Seq(
  "g00dnatur3" %% "jscover-play-utils" % "1.0.05"
)
```
There are three primary settings `jscoverSourcePath`, `jscoverDestinationPath`, and `jscoverReportsDir`

The `jscoverSourcePath` is the location where to find all your javascript files.

Default value: `public/javascripts`

The `jscoverDestinationPath` is where the (generated) instrumented javascript files will be put.

Default value: `public/jscover`

The `jscoverReportsDir` is where the coverage reports json will be put. The FluentTestWithCoverage found within the `jscover-play-utils` dependency will actually generate the reports json for you.

Default value: `public/jscover/reports`

If you want to customize these settings, you can do the following inside your `build.sbt` file:

```
JSCoverPlugin.jscoverSettings

jscoverSourcePath := "public/<your custom source path>"

jscoverDestinationPath := "public/<your custom destination path>"

jscoverReportsDir := "public/<your custom reports dir>"

buildInfoSettings

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq[BuildInfoKey](jscoverSourcePath, jscoverDestinationPath, jscoverReportsDir)

buildInfoPackage := "sbt"

resolvers += "play-utils" at "http://dl.bintray.com/g00dnatur3/play-utils/"

libraryDependencies ++= Seq(
  "g00dnatur3" %% "jscover-play-utils" % "1.0.06"
)
```
IMPORTANT:  If you do decided to customize these settings, make sure to keep them within the "public" folder (for obvious reasons)


Writing a Test
-------------------------

This is the easiest way to create a test:

```
public class ExampleTest extends FluentTestWithCoverage {
	
	@Page
	LandingPage landingPage;
	
	@Test
	public void testInBrowser() {
		  goTo(landingPage);
		  landingPage.isAt();
	}
	
}
```

Writing a Test with custom CoverageTestSettings
-------------------------

the FluentTestWithCoverage has a method `public CoverageTestSettings createTestSettings()`

You can override this method to customize your test settings, for example:

```
@Override
public CoverageTestSettings createTestSettings() {

	return new CoverageTestSettings() {
		
	    @Override
	    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
	        ...
	    }
	
	    @Override
	    public <T extends EssentialFilter> Class<T>[] filters() {
	        ...
	    }
	    
	    @Override
	    public void onStart(Application application) {
	    	...
	    }
		
	};
}
```
If you cannot tell, `CoverageTestSettings` extends `GlobalSettings`

Viewing the Coverage Report
-------------------------

The command `play test` should execute your test cases and generate the final report.

Now all you have left to do is view it!

This is simple, just do `play run` and open your browser to the following url `http://localhost:9000/jscover/jscoverage.html`

You should see the report.

If you have customized the `jscoverDestinationPath` to be something different, then the url will be different.

For example, if your `jscoverDestinationPath` is customized to "public/coverage" then the url would be `http://localhost:9000/coverage/jscoverage.html`

