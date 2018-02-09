
name := "stock-indexs"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.2.0" excludeAll(
  ExclusionRule(organization = "org.apache.spark", name = "spark-tags_2.11"),
  ExclusionRule(organization = "org.spark-project.spark", name = "unused"))

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.2.0"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.2.0"

libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.25"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"


