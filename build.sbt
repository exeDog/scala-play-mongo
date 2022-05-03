name := """play-example"""
organization := "com.priyank"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

libraryDependencies += guice
libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.20.13-play27"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.priyank.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.priyank.binders._"
play.sbt.routes.RoutesKeys.routesImport += "play.modules.reactivemongo.PathBindables._"

Global / onChangedBuildSource := ReloadOnSourceChanges
