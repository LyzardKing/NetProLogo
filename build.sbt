import org.nlogo.build.NetLogoExtension
// import scala.sys.process._

enablePlugins(NetLogoExtension)

name := "netprologo"
version := "1.0.0"
isSnapshot := true

javacOptions ++= Seq("-source", "17", "-target", "17")

Compile / javaSource := baseDirectory.value / "src"
// Compile / unmanagedJars ++= Seq(new java.io.File("/usr/lib/swi-prolog/lib/jpl.jar")).classpath

netLogoVersion := "6.3.0"
netLogoClassManager := "main.NetPrologoExtension"

resolvers += "jitpack" at "https://jitpack.io"
libraryDependencies += "com.github.SWI-Prolog" % "packages-jpl" % "V9.+"
// 
// // Read libPath from swipl cli
// val libPath = "swipl --dump-runtime-variables | grep \"PLLIBDIR\" | cut -d\"\\\"\" -f2" !
// 
// // Default libPath for Linux
// // val libPath = "/usr/lib/swi-prolog/lib/x86_64-linux"
// run / javaOptions += s"-Djava.library.path=$libPath"
// run / fork := true