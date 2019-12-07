// Settings file for all the modules.
import xml.Group
import sbt._
import Keys._
import Defaults._

organization := "io.swagger"

version := "1.0.6-SNAPSHOT"

scalaVersion := "2.11.12"

crossScalaVersions := Seq("2.10.6", scalaVersion.value, "2.12.6", "2.13.0")

organizationHomepage in ThisBuild := Some(url("http://swagger.io"))

scalacOptions in ThisBuild ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked")  

publishMavenStyle in ThisBuild := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "io.swagger" % "swagger-core" % "1.5.22",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.9",
  "junit" % "junit" % "4.12" % "test"
)

publishTo := {
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
  else
    Some("Sonatype Nexus Releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
}

credentials in ThisBuild += Credentials (Path.userHome / ".ivy2" / ".credentials")

resolvers in ThisBuild ++= Seq(
  Resolver.mavenLocal,
  Resolver.typesafeRepo("releases"),
  Resolver.typesafeRepo("snapshots"),
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

homepage := Some(new URL("https://github.com/swagger-api/swagger-scala-module"))

parallelExecution in Test := false

startYear := Some(2014)

licenses := Seq(("Apache License 2.0", new URL("http://www.apache.org/licenses/LICENSE-2.0.html")))

pomExtra := {
  pomExtra.value ++ Group(
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
  )
}
