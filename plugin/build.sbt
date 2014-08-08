import bintray.Keys._

sbtPlugin := true

name := "jscover-sbt-plugin"

organization := "g00dnatur3"

version := "1.0.05"

publishMavenStyle := false

bintrayPublishSettings

repository in bintray := "sbt-plugins"

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

bintrayOrganization in bintray := None

libraryDependencies ++= Seq(
  "com.github.tntim96" % "JSCover" % "1.0.13",
  "commons-io" % "commons-io" % "2.4"
)