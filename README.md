jscover-sbt-plugin
==================

A plugin for Play Framework to enable getting javascript code coverage of functional tests thru the use of JSCover library.

This plugin was developed with Play Framework 2.2.1, but should work for all 2.2.x versions (and maybe even 2.3.x)

Installation Guide
-------------------------

First, add the following to the `project/plugins.sbt` file:
```
resolvers += Resolver.url("sbt-plugins", url("http://dl.bintray.com/g00dnatur3/sbt-plugins"))(Resolver.ivyStylePatterns)

addSbtPlugin("g00dnatur3" % "jscover-sbt-plugin" % "1.0.06")

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
  "g00dnatur3" %% "jscover-play-utils" % "1.0.10"
)
```
There are four settings `jscoverSourcePath`, `jscoverDestinationPath`, `jscoverReportsDir`, and `jscoverNoInstrumentPaths`

The `jscoverSourcePath` is the location where to find all your javascript files.

Default value: `public/javascripts`

The `jscoverDestinationPath` is where the (generated) instrumented javascript files will be put.

Default value: `public/jscover/javascripts`

The `jscoverReportsDir` is where the coverage reports json will be put. The FluentTestWithCoverage found within the `jscover-play-utils` dependency will actually generate the reports json for you.

Default value: `public/jscover/reports`

The `jscoverNoInstrumentPaths` is a comma-delimited list of paths to ignore when instrumenting the javascript.

Default value: `null`

If you want to customize these settings, you can do the following inside your `build.sbt` file:

```
JSCoverPlugin.jscoverSettings

jscoverSourcePath := "public/<your custom source path>"

jscoverDestinationPath := "public/<your custom destination path>"

jscoverReportsDir := "public/<your custom reports dir>"

jscoverNoInstrumentPaths := "jquery-1.9.0.min.js,lib"

buildInfoSettings

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq[BuildInfoKey](jscoverSourcePath, jscoverDestinationPath, jscoverReportsDir)

buildInfoPackage := "sbt"

resolvers += "play-utils" at "http://dl.bintray.com/g00dnatur3/play-utils/"

libraryDependencies ++= Seq(
  "g00dnatur3" %% "jscover-play-utils" % "1.0.10"
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

You can find more information on how to write a FluentLenium test here: 
`https://github.com/FluentLenium/FluentLenium`

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

This is simple, just do `play run` and open your browser to the following url `http://localhost:9000/jscover/javascripts/jscoverage.html`

You should see the report.

If you have customized the `jscoverDestinationPath` to be something different, then the url will be different.

For example, if your `jscoverDestinationPath` is customized to "public/coverage" then the url would be `http://localhost:9000/coverage/jscoverage.html`

If any tests fail the final merged coverage report will not be generated and available for viewing.
To run individual test classes, go into the play console and run `testOnly package.Classname`

Known issues and workarounds:
-------------------------

The routing bug:

```
[error] Unspecified value parameter file.
[error]         <link rel="stylesheet" media="screen" href="@routes.Assets.at("stylesheets/main.css")">
```

If you have the following bug using the plugin, this is because of a routing issue.

The jscover-sbt-plugin will append a new route to the bottom of the `conf/routes` file.

```
# This route was generated by jscover-sbt-plugin
GET    /jscover/javascripts/*file    controllers.Assets.at(path="/public/jscover/javascripts", file)
```

This route does not break the compile, however if you are using the routing technique of `@routes.Assets.at("javascripts/jquery-1.9.0.min.js")` inside your scala views, then you will have a problem.

Most likely you also have the following route inside your `conf/routes` file

```
# Map static resources from the /public folder to the /assets URL path
GET     /assets/*file               controllers.Assets.at(path="/public", file)
```

To solve this problem, change `@routes.Assets.at("javascripts/jquery-1.9.0.min.js")` to be `/assets/javascripts/jquery-1.9.0.min.js` inside your scala views

This is a better approach anyways, I never did like the default `@routes.Assets.at(` making my html look dirty.

That is how I recommend to solve the problem, otherwise you can take a look here `http://www.playframework.com/documentation/2.0/JavaRouting` and figure out a solution that works for you.

In any case, learning and understanding Play Framework routing is beneficial.


Cheers!







