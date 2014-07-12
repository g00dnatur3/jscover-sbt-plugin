jscover-sbt-plugin
==================

A plugin for sbt to enable getting javascript code coverage for functional style tests thru the use of JSCover library.


Installation Guide
-------------------------
To use the SBT plugin, add the following to the `project/plugins.sbt` file:
```
resolvers += Resolver.url("sbt-plugins", url("http://dl.bintray.com/g00dnatur3/sbt-plugins"))(Resolver.ivyStylePatterns)

addSbtPlugin("g00dnatur3" % "jscover-sbt-plugin" % "1.0.02")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.3.2")
```
