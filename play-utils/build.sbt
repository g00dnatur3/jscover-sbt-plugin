import bintray.Keys._

name := "jscover-play-utils"

organization := "g00dnatur3"

version := "1.0.10"

bintrayPublishSettings

repository in bintray := "play-utils"

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

bintrayOrganization in bintray := None

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.2.1" % "provided",
  "com.typesafe.play" %% "play-test" % "2.2.1" % "provided",
  "org.seleniumhq.selenium" % "selenium-java" % "2.41.0",
  "org.seleniumhq.selenium" % "selenium-server" % "2.41.0",
  "org.seleniumhq.selenium" % "selenium-remote-driver" % "2.41.0",
  "com.github.detro.ghostdriver" % "phantomjsdriver" % "1.1.0"
)

