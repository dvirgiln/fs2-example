organization := "com.david"
version := "0.1-SNAPSHOT"
name := "fs2-example"
scalaVersion := "2.13.8"

val commonDependencies: Seq[ModuleID] = Seq(
  "org.slf4j" % "slf4j-log4j12" % "1.7.10",
  "org.apache.spark" %% "spark-core" % "3.5.0",
  "co.fs2" %% "fs2-core" % "3.9.2"
)

val log4j : Seq[ModuleID] = Seq("log4j" % "log4j" % "1.2.17")


val root = (project in file(".")).
  settings(
    libraryDependencies ++= commonDependencies,
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:_"
    )
  )

