
name := "stock-index-statistics"
organization := "com.thoughtworks"
scalaVersion := "2.11.8"

val buildVersion = sys.env.get("GO_PIPELINE_LABEL") match {
  case Some(label) => s"$label"
  case _ => "1.0-SNAPSHOT"
}

credentials += Credentials("Sonatype Nexus Repository Manager", "nexus.tdp.domain", "yzh", "1q2w3e4r")

publishTo := Some("Sonatype Nexus" at "https://nexus.tdp.domain/repository/maven-releases/")

lazy val libSettings = Seq(
  version := buildVersion,
  scalaVersion := "2.11.8",
  organization := "com.thoughtworks"
)

lazy val root = (project in file("."))
  .settings(libSettings: _*)
  .settings(SparkSubmit.settings: _*)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}


libraryDependencies += "org.apache.spark" %% "spark-core" % "2.2.0"

libraryDependencies += "org.apache.spark" %% "spark-yarn" % "1.4.0"

libraryDependencies += "org.apache.spark" %% "spark-hive" % "2.2.0"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.2.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % Test

libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.2.1"

libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-8" % "2.0.0-preview"

libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.25"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

libraryDependencies += "com.hankcs" % "hanlp" % "portable-1.5.3"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.1.0"

libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.1.1"















