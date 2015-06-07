// Settings file for all the modules.
import xml.Group
import sbt._
import Keys._
import Defaults._

organization := "io.swagger"

version := "1.0.1-SNAPSHOT"

scalaVersion := "2.10.4"

crossScalaVersions := Seq("2.10.0", "2.10.1", "2.10.2", "2.10.3", "2.10.4", "2.11.0", "2.11.1", "2.11.4")

organizationHomepage in ThisBuild := Some(url("http://swagger.io"))

scalacOptions in ThisBuild ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked")  

publishMavenStyle in ThisBuild := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

libraryDependencies ++= Seq(
  "io.swagger" % "swagger-core" % "1.5.0",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.4.2",
  "junit" % "junit" % "4.11" % "test"
)

publishTo <<= (version) { version: String =>
  if (version.trim.endsWith("SNAPSHOT"))
    Some("Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
  else
    Some("Sonatype Nexus Releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
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

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

homepage := Some(new URL("https://github.com/swagger-api/swagger-scala-module"))

parallelExecution in Test := false

startYear := Some(2014)

licenses := Seq(("Apache License 2.0", new URL("http://www.apache.org/licenses/LICENSE-2.0.html")))

pomExtra <<= (pomExtra, name, description) {(pom, name, desc) => pom ++ Group(
  <scm>
    <connection>scm:git:git@github.com:swagger-api/swagger-scala-module.git</connection>
    <developerConnection>scm:git:git@github.com:swagger-api/swagger-scala-module.git</developerConnection>
    <url>https://github.com/swagger-api/swagger-scala-module</url>
  </scm>
  <issueManagement>
    <system>github</system>
    <url>https://github.com/swagger-api/swagger-scala-module/issues</url>
  </issueManagement>
  <developers>
    <developer>
      <id>fehguy</id>
      <name>Tony Tam</name>
      <email>fehguy@gmail.com</email>
    </developer>
  </developers>
)}
