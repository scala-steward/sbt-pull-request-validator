lazy val `sbt-pull-request-validator` = project in file(".")

sbtPlugin := true

organization := "com.hpe.sbt"

homepage := Some(url("https://github.com/sbt/sbt-pull-request-validator"))
licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

// https://mvnrepository.com/artifact/org.kohsuke/github-api
libraryDependencies += "org.kohsuke" % "github-api" % "1.93"

scriptedLaunchOpts ++= Seq(
  "-Dplugin.version=" + version.value
)
scriptedBufferLog := false

// Bintray
bintrayOrganization := Some("sbt")
bintrayRepository := "sbt-plugin-releases"
bintrayPackage := "sbt-release"
bintrayReleaseOnPublish := false

// Release
import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  releaseStepTask(test),
  releaseStepCommandAndRemaining("scripted"),
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepTask(publish),
  releaseStepTask(bintrayRelease in `sbt-pull-request-validator`),
  setNextVersion,
  commitNextVersion,
  pushChanges
)