// Settings file for all the modules.
import sbt._
import Keys._
import Defaults._

// Blank lines are required between settings.

organization in ThisBuild := "io.swagger"

version in ThisBuild := "1.0.0-SNAPSHOT"

scalaVersion := "2.10.4"

crossScalaVersions := Seq("2.10.4", "2.11.4")

crossPaths in ThisBuild := false

organizationName in ThisBuild := "Swagger API"

organizationHomepage in ThisBuild := Some(url("http://swagger.io"))

scalacOptions in ThisBuild ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked")  

publishMavenStyle in ThisBuild := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

libraryDependencies ++= Seq(
  "com.wordnik" % "swagger-core" % "1.5.3-M1-SNAPSHOT",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.4.2",
  "junit" % "junit" % "4.11" % "test"
)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

credentials in ThisBuild += Credentials (Path.userHome / ".ivy2" / ".credentials")

resolvers in ThisBuild ++= Seq(
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
  Resolver.url("Local Ivy Repository", url("file://"+Path.userHome.absolutePath+"/.ivy2/local"))(Resolver.ivyStylePatterns),
  "Typesafe Releases Repository" at "http://repo.typesafe.com/typesafe/releases/",
    "Typesafe Snapshots Repository" at "http://repo.typesafe.com/typesafe/snapshots/",
    "Maven Central" at "http://repo1.maven.org/maven2",
  "Typesafe Maven Releases Repository" at "https://typesafe.artifactoryonline.com/typesafe/maven-releases/",
    "Typesafe Maven Snapshots Repository" at "https://typesafe.artifactoryonline.com/typesafe/maven-snapshots/",
    "sonatype-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases")
