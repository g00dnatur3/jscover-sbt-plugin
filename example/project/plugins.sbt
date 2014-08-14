// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.1")

//resolvers += Resolver.url("sbt-plugins", url("http://dl.bintray.com/g00dnatur3/sbt-plugins"))(Resolver.ivyStylePatterns)

//addSbtPlugin("g00dnatur3" % "jscover-sbt-plugin" % "1.0.05")

lazy val root = project.in( file(".") ).dependsOn(
	uri("file:///Users/user/jscover-sbt-plugin/plugin")
)

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.3.2")