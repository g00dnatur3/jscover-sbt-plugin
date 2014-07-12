jscover-sbt-plugin
==================

A plugin for sbt to enable getting javascript code coverage for functional style tests thru the use of JSCover library.

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

buildInfoKeys := Seq[BuildInfoKey](jscoverSourcePath, jscoverDestinationPath, jscoverReportsDir, name)

buildInfoPackage := "sbt"
```
There are threee primary settings `jscoverSourcePath`, `jscoverDestinationPath`, and `jscoverReportsDir`

The `jscoverSourcePath` is the location where to find all your javascript files.

Default value: `public/javascripts`
