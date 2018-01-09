lazy val `sam-encryption-test` = (project in file("."))
  .settings(
    libraryDependencies += "com.github.dnvriend" %% "sam-annotations" % "1.0.16",
    libraryDependencies += "com.github.dnvriend" %% "sam-lambda" % "1.0.16",
    libraryDependencies += "com.github.dnvriend" %% "sam-serialization" % "1.0.16",
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-core" % "1.2.0",
    libraryDependencies += "org.bouncycastle" % "bcprov-ext-jdk15on" % "1.54",
    libraryDependencies += "com.amazonaws" % "aws-encryption-sdk-java" % "1.3.1",
    resolvers += Resolver.bintrayRepo("dnvriend", "maven"),
    scalaVersion := "2.12.4",
    samStage := "dn",
    organization := "com.github.dnvriend",
    description := "simple sam component with endpoints"
  )


