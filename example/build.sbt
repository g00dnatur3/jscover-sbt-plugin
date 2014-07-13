name := "example"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

play.Project.playJavaSettings

JSCoverPlugin.jscoverSettings

buildInfoSettings

sourceGenerators in Compile <+= buildInfo

buildInfoKeys := Seq[BuildInfoKey](jscoverSourcePath, jscoverDestinationPath, jscoverReportsDir)

buildInfoPackage := "sbt"

resolvers += "play-utils" at "http://dl.bintray.com/g00dnatur3/play-utils/"

libraryDependencies ++= Seq(
  "g00dnatur3" %% "jscover-play-utils" % "1.0.10"
)