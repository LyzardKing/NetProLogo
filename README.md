# NetProLogo

NetProLogo ([NetLogo](http://en.wikipedia.org/wiki/NetLogo) + [Prolog](http://en.wikipedia.org/wiki/Prolog)) is a NetLogo extension that allows running Prolog code inside NetLogo in order to take advantage of Prolog features to provide NetLogo agents (turtles, links and patches... or the observer) with reasoning capabilities.

This repo contains an updated version compatible with SWI-Prolog, and uses the [JPL](http://www.swi-prolog.org/packages/jpl/) library that comes with SWI-Prolog and provides a Java API to interact with it. 

## Download

The last NetProLogo version can be downloaded [here](https://github.com/jgalanp/NetProLogo/releases/download/v0.2/NetProLogo.zip).

The extension comes with a set of sample models in order to enlighten extension usage. Some of the sample models load an auxiliary Prolog file from the same directory using absolute paths, thus it could be necessary to fix the path inside the NetLogo model in order to point to the required prolog file.

Any feedback you can provide regarding use cases, installation issues or future improvements will be very appreciated.

## Important notes

Before using the extension make sure you have installed the proper [NetLogo](http://ccl.northwestern.edu/netlogo/download.shtml) and SWI-Prolog versions. Different SWI-Prolog versions can be found [here](http://www.swi-prolog.org/download/stable?show=all).

### Version compatibility

* NetPrologo is compatible with SWI-Prolog version 9.0.4 (and later) for x86_64-linux. Previous versions are not guaranteed to work.
* Macos and Windows are not tested, but should work with the right classpath settings

### Prerequisites

* Note that some SWI-Prolog packages come without the JPL library. In this case you can install it separately.
  * In many Linux repositories the complete package is called `swi-prolog-java` (alternatively it could be swi-prolog-jpl).
  * You can find the path that contains these libraries by running `swipl --dump-runtime-variables`, and reading the value of `PLLIBDIR`
  * check that `PLLIBDIR` contains the native library:
    * **Windows:** `C:\Program Files (x86)\swipl\bin\` (there should be files like `libswipl.dll`, `jpl.dll`, etc.)
      * [How to set the path and environment variables in Windows](http://www.computerhope.com/issues/ch000549.htm)
    * **Mac OS:** `/opt/local/lib/swipl-x.x.x/lib/x86_64-darwinx.x.x/` (there should be files like `libjpl.dylib`)
    * **Linux:** `/usr/lib/swi-prolog/lib/x86_64-linux/` (there should be files like `libjpl.so`)


## Installation

* Go to the NetLogo folder. In `extensions` folder extract the content of the Zip file in a netprologo folder.

### Linux (and macOS)

* To use the bundled version of Java you can run the `Netlogo` file
  * Before you run check that you have the jpl.jar file in the `extensions/netprologo` folder (should be included by default)
  * Modify the `lib/app/netlogo.cfg` file, and add the following just before the last line:\
  `java-options=-Djava.library.path=<PLLIBDIR>`

* To use the system version of Java you can run the `netlogo-gui.sh` file
  * Before you run it you should edit it, and add the following just before the last line:\
  `JVM_OPTS=($JVM_OPTS -Djava.library.path="<PLLIBDIR>")`\
  `ABSOLUTE_CLASSPATH="$ABSOLUTE_CLASSPATH:<PLLIBDIR parent>/jpl.jar"`

### Windows

* To use the bundled version of Java you can run `Netlogo`
  * Before you run check that you have the jpl.jar file in the `extensions/netprologo` folder (should be included by default)
  * Modify the `app/NetLogo.cfg` file, and add the following just before the last line:\
  `java-options=-Djava.library.path=<PLLIBDIR>`

* To use the system version of Java you can run the `netlogo-gui.bat` file
  * Before you run it you should edit it, and add the following just before the last line:\
  `JVM_OPTS=($JVM_OPTS -Djava.library.path="<PLLIBDIR>")`\
  `ABSOLUTE_CLASSPATH="$ABSOLUTE_CLASSPATH:<PLLIBDIR parent>/jpl.jar"`

## How to use NetProLogo

In order to use NetProLogo load the extension in the first line of the model:
```netlogo
extensions[netprologo]
```

**Note for Windows users:** When typing paths inside Prolog code you must use Unix flavor paths (i.e. using slash `/`, not backslash `\`).

The following primitives are available:

* **Open a new Prolog query:** `<Boolean> netprologo:run-query <PrologCall-String>`. This primitive will close a former query after doing the call, thus it will not be possible to obtain more solutions from it. It returns `true` if the query was successful (to obtain query results see `run-next` and `dereference-var`) or the solution is true, or `false` otherwise.
* **Build Prolog Call:** `<Query-String> netprologo:build-prolog-call <Rear-Query-String> <Value-1> ... <Value-N>`. It is useful to make automatic arguments replacement in order to build the Prolog call, especially when using lists as arguments. Arguments are automatically translated from NetLogo to Prolog. The place of every argument should be marked with `?X` in the `Rear-Query-String` where `X` is an integer argument ID (as it is common in NetLogo). The same argument can be used more than once by placing the same ID twice in the `Rear-Query-String`. Example:

 ```netlogo
 let call (netprologo:build-prolog-call "assert(fact(?1,?2,?3,?2))" nl-arg1 nl-arg2 nl-arg3)
 ```
 The result will be something like:

 ```prolog
 assert(fact(nl-arg1,nl-arg2,nl-arg3,nl-arg2))
 ```
* **Advance till next solution:** `<Boolean> netprologo:run-next <>`. Load the next solution of the open query. It returns `false` if there are no more results to be read, in this case, the query is closed automatically. If it returns `true`, this next solution is ready to be read  (see `dereference-var`).
* **Read the value of a variable for the last loaded solution:** `<List/String/Number> netprologo:dereference-var <Var-name>`. In the current version it can read `Strings`, numbers (`Float` or `Integer`) and `NetLogo lists` made of any of the former elements, including `nested lists`. Currently, other prolog data types like `functors` are not supported.
* **Close active query:** `<> netprologo:close-query <>`. Closes the last active query. Normally this primitive is not necessary since `run-query` primitive automatically closes the query before opening a new one.
* **Make a Prolog query to obtain several solutions:** In the former NetProLogo version (based on a GPJ) it was possible to have multiple Prolog instances running at the same time (and therefore multiple active queries). However in the current version this is not longer possible, as it is just an interface between NetLogo and the local SWI-Prolog installation. That is why some primitives have been added to allow querying and storing multiple solutions, to be read later, before running a new query:
 * **Open a query and store N solutions:** `<Solution-storage> run-for-n-solutions <N> <PrologCall-String>`. Opens a new Prolog query, reads and store the first `N` solutions. It returns a reference to the solutions storage.
 * **Advance till next stored solution:** `<Boolean> netprologo:next-stored-solution <Solution-storage>`. Load the next solution of the stored query. It returns `false` if there are no more results to be read. If it returns `true`, this next solution is ready to be read  (see `dereference-stored-var`).
 * **Read the value of a variable for the last loaded solution from storage:** `<List/String/Number> netprologo:dereference-stored-var <Solution-storage> <Var-name>`. In the current version it can read `Strings`, numbers (`Float` or `Integer`) and `NetLogo lists` made of any of the former elements, including `nested lists`. Other Prolog data types like `functors` are not supported.
