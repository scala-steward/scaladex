addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.8.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % "1.5.1")
addSbtPlugin("org.irundaia.sbt" % "sbt-sassify" % "1.4.13")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.2.2")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.8.0")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.1.0")
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.34")

libraryDependencies ++= Seq(
  "org.testcontainers" % "postgresql" % "1.16.0",
  "org.testcontainers" % "elasticsearch" % "1.16.0",
  "org.tpolecat" %% "doobie-postgres" % "0.13.4"
)
