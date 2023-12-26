import org.nlogo.build.NetLogoExtension

enablePlugins(NetLogoExtension)

name := "netprologo"
version := "1.0.0"
isSnapshot := true

Compile / javaSource := baseDirectory.value / "src"
Compile / unmanagedJars ++= Seq(new java.io.File("/usr/lib/swi-prolog/lib/jpl.jar")).classpath

netLogoVersion := "6.3.0"
netLogoClassManager := "main.NetPrologoExtension"
