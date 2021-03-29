import Dependencies._

name := "lagom-mysql-poc"

version := "0.1"

scalaVersion := "2.12.8"

lagomKafkaEnabled in ThisBuild := false

lazy val `cassandra-to-sql-poc` = (project in file("."))
  .aggregate(`user-api`, `user-impl`)

lazy val `user-api` = (project in file("user-api"))
  .settings(libraryDependencies ++= Seq(lagomScaladslApi))
  .settings(coverageExcludedPackages := ".*UserService.*;")

lazy val `user-impl` = (project in file("user-impl"))
  .enablePlugins(LagomScala)
  .settings(resolvers += "OAM 11g" at "https://maven.oracle.com",
    credentials += Credentials("OAM 11g", "login.oracle.com", "shashi.rsb@hotmail.com", "Hitmewell123"),
    libraryDependencies += "com.oracle.ojdbc" % "ojdbc8" % "19.3.0.0"
    )
  .settings(    
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceJdbc,
      javaJdbc
    )
  .settings(
    libraryDependencies ++= Seq(lagomScaladslTestKit, MacWire, FilterHelper, ScalaTest,
      lagomScaladslPersistenceJdbc, lagomScaladslApi, Mockito, MySqlConnector, TypeSafeConf))
  .settings(
    coverageExcludedPackages := ".*UserLoader.*;.*UserSerializerRegistry.*;.*UserApplication.*;.*UserConstants.*;")
  .dependsOn(`user-api`)

lagomCassandraEnabled in ThisBuild := false

dependencyOverrides in ThisBuild ++= Seq(ScalaParser, NettyHandler, AkkaStream, AkkaActor, Guava)
