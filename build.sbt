import org.nlogo.build.NetLogoExtension

enablePlugins(NetLogoExtension)

name := "netprologo"
version := "1.0.0"
isSnapshot := true

javacOptions ++= Seq("-source", "17", "-target", "17")

Compile / javaSource := baseDirectory.value / "src"

netLogoVersion := "6.3.0"
netLogoClassManager := "main.NetPrologoExtension"

resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies += "com.github.SWI-Prolog" % "packages-jpl" % "V9.+"