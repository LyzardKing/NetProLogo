JFLAGS = -classpath /usr/lib/swi-prolog/lib/jpl.jar:netlogo-6.4.0.jar -d classes
JC = javac

.SUFFIXES: .java .class

# CLASSES = src/main/NetPrologoExtension.java src/primitives/*.java src/utils/*.java

jar: build
	jar cvfm netprologo.jar manifest.txt -C classes .

build:
	$(JC) $(JFLAGS) src/main/NetPrologoExtension.java src/primitives/*.java src/utils/*.java

clean:
	$(RM) -rf classes netprologo.jar