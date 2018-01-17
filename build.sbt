import scoverage.ScoverageKeys

name := "gfc-aws-cloudwatch"

organization := "com.gilt"

scalaVersion := "2.12.4"

crossScalaVersions := Seq(scalaVersion.value)


val awsLibVersion = "2.0.0-preview-7"

scalacOptions += "-target:jvm-1.8"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

ScoverageKeys.coverageFailOnMinimum := true

ScoverageKeys.coverageMinimum := 11.5

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0",
  "com.gilt" %% "gfc-logging" % "0.0.8"
, "com.gilt" %% "gfc-concurrent" % "0.3.7"
, "software.amazon.awssdk" % "cloudwatch" % awsLibVersion
, "software.amazon.awssdk" % "logs" % awsLibVersion
, "org.specs2" %% "specs2-scalacheck" % "4.0.2" % Test
)

releaseCrossBuild := true

releasePublishArtifactsAction := PgpKeys.publishSigned.value

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

licenses := Seq("Apache-style" -> url("https://raw.githubusercontent.com/gilt/gfc-aws-cloudwatch/master/LICENSE"))

homepage := Some(url("https://github.com/gilt/gfc-aws-cloudwatch"))

pomExtra := <scm>
  <url>https://github.com/gilt/gfc-aws-cloudwatch.git</url>
  <connection>scm:git:git@github.com:gilt/gfc-aws-cloudwatch.git</connection>
</scm>
<developers>
  <developer>
    <id>gheine</id>
    <name>Gregor Heine</name>
    <url>https://github.com/gheine</url>
  </developer>
  <developer>
    <id>andreyk0</id>
    <name>Andrey Kartashov</name>
    <url>https://github.com/andreyk0</url>
  </developer>
</developers>

