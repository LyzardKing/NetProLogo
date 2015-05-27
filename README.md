# NetProLogo

NetProLogo ([NetLogo](http://en.wikipedia.org/wiki/NetLogo) + [Prolog](http://en.wikipedia.org/wiki/Prolog)) is a NetLogo extension that allows running Prolog code inside NetLogo in order to take advantage of Prolog features to provide NetLogo agents (turtles, links and patches... or the observer) with reasoning capabilities.

There exist two major NetProLogo versions depending on the Prolog engine they use:

##### NetProLogo for SWI-Prolog

This is the NetPrologo version currently active. We have changed to [SWI-Prolog](http://www.swi-prolog.org/) since it is the most widely used Prolog engine and it supports most of the existing Prolog code and extensions. This extension uses the [JPL](http://www.swi-prolog.org/packages/jpl/) library that comes with SWI-Prolog and provides a Java API to interact with it. However, this NetProLogo version presents two drawbacks with respect to the former one:

* The extension allows having only one open Prolog call at once. The former version of the extension allowed having multiple instances of the Prolog engine running simultaneously.
* The installation of the extension is OS dependent as it relies on the SWI-Prolog installed on the system.

##### NetProLogo for GPJ (GNU Prolog for Java)

This is a NetProLogo old version which is not being maintained anymore. However it is still available in this [site](http://www.cs.us.es/~fsancho/NetProLogo/). This version of the extension was ruled since [GPJ](http://www.gnu.org/software/gnuprologjava/gnuprologjava.html) supports only basic Prolog sintax, therefore it was not compatible with most of the existing Prolog code and extensions. Moreover its development was discontinued. The advantages of using this extension are:

* Cross-platform compatibility. GPJ was entirely devoloped in Java.
* Supports running multiple instances of the Prolog engine simultaneously, which can be very useful in multi-agent programming since it allows each agent having its own reasoner and knowledge base.

## Download

The last NetProLogo version can be downloaded [here](https://github.com/jgalanp/NetProLogo/releases/download/v0.2/NetProLogo.zip).

The extension comes with a set of sample models in order to englihten extension usage. Some of the sample models load an auxiliar Prolog file from the same directory using absolute paths, thus it will be necessary to correct that path (inside the NetLogo model) in order to point to the required prolog file.

Any feedback you can provide regarding use cases, installation issues or future improvements will be very appreciated.

## Important notes

Before using the extension make sure you have installed the proper [NetLogo](http://ccl.northwestern.edu/netlogo/download.shtml) and SWI-Prolog versions. In this [link](http://www.swi-prolog.org/download/stable?show=all) you can find the different SWI-Prolog versions.

##### Version compatibility

* NetProLogo was developed for NetLogo 5.x
* NetPrologo is compatible with SWI-Prolog 6.6.x and earlier versions. Note that the new SWI-Prolog 7.x is not supported yet.

##### Windows users

* Make sure you install a 32 bits SWI-Prolog. 64 bits version is not compatible since NetLogo uses a 32 bits JVM.

##### Mac users

* Application bundle is not supported (SWI-Prolog 6.6x). You can install SWI-Prolog 6.6.x using [Macports](http://www.swi-prolog.org/build/macos.html) or use [SWI-Prolog installers](http://www.swi-prolog.org/download/stable?show=all) for versions 6.4.x and earlier. The reason is that the extension needs SWI-Prolog to be installed in system's root and not in Mac OS Applications folder.

##### Linux Users

* Note that some SWI-Prolog packages for Linux comes without the JPL library. In this case you can install it separately.
  * In many Linux repositopries this library is called `swi-prolog-java` (alternatively it could be swi-prolog-jpl).
  * You can find out if this library is installed in your system by looking, within your SWI-Prolog installation, for the files `jpl.jar` and `libjpl.so`. The default paths of these files for SWI-Prolog 6.4.x in Ubuntu Trusty (14.04 LTS) are:
  * `/usr/lib/swi-prolog/lib/amd64/libjpl.so`
  * `/usr/lib/swi-prolog/lib/jpl.jar`

## Installation

* At this point you should have SWI-Prolog properly installed in your system (following the notes above).
* Go to the NetLogo folder. In `extensions` folder copy NetPrologo files. The files must look like:
 * `/NetLogo x.x.x/extensions/netprologo/jpl.jar`
 * `/NetLogo x.x.x/extensions/netprologo/netprologo.jar`
 * `/NetLogo x.x.x/extensions/netprologo/config.txt` (this file is optional, details below)
* Add SWI-Prolog binaries path to your system PATH. These binaries are usualy found in:
 * **Windows:** `C:\Program Files (x86)\swipl\bin\` (there should be files like `libswipl.dll`, `jpl.dll`, etc)
   * [How to set the path and environment variables in Windows](http://www.computerhope.com/issues/ch000549.htm)
 * **Mac OS:** `/opt/local/lib/swipl-x.x.x/lib/x86_64-darwinx.x.x/` (there should be files like `libjpl.so`)
 * **Linux:** `/usr/lib/swi-prolog/lib/amd64/` (there should be files like `libjpl.dylib`, `libswipl.dylib`, etc.)
 * **IMPORTANT:** For Linux and Mac OS users, this step is not mandatory. The extension can set `java.library.path` programmatically. In this case you just have to add SWI-Prolog binaries path to the file `config.txt`. If you are not going to use this option delete `config.txt` file.

## How to use NetPrologo

In order to use NetProLogo load the extension in the first line of the model:
```netlogo
extensions[netprologo]
```

**Note for Windows users:** When typing paths inside prolog code use Unix flavoured paths (i.e. using slash `/`, not backslash `\`).

The following primitives are available:

* **Opens a new Prolog query:** `<Boolean> netprologo:run-query <PrologCall-String>`. This primitive will close a former query after doing the call, thus it will not be possible to obtain more solutions from it. It returns `true` if the query was successful (to obtain query results see `run-next` and `dereference-var`) or the solution is true, or `false` otherwise.
* **Builds Prolog Call:** `<Query-String> netprologo:build-prolog-call <Rear-Query-String> <Value-1> ... <Value-N>`. It is useful to make automatic arguments replacement in order to build the prolog call, specially when using lists as arguments. Arguments are automatically translated from NetLogo to Prolog. The place of every argument should be marked with `?X` in the `Rear-Query-String` where `X` is an argument ID (integer). The same argument can be used more than once by placing the same ID twice in the 'Rear-Query-String'. Example:

 ```netlogo
 let call (netprologo:build-prolog-call "assert(fact(?1,?2,?3,?2))" nl-arg1 nl-arg2 nl-arg3)
 ```
 The result will be something like:

 ```prolog
 assert(fact(nl-arg1,nl-arg2,nl-arg3,nl-arg2))
 ```
* **Advances till next solution:** `<$Boolean> netprologo:run-next <>`. Load the next solution of the open query. It returns `false` if there are any more results to be read, in this case, the query is closed automatically. If it returns `true`, this next solution is ready to be read  (see `dereference-var`).
* **Reads the value of a variable for the last loaded solution:** `<List/String/Number> netprologo:dereference-var <Var-name>`. In the current version it can read `Strings`, numbers (`Float` or `Integer`) and `NetLogo lists` made of any of the former elements, including `nested lists`. Thus other prolog data types like `functors` are not actually supported.
* **Closes active query:** `<> netprologo:close-query <>`. Closes the last active query. Normally this primitive is not necessary since `run-query` primitive automatically closes the query before opening a new one.
* **Makes a Prolog query to obtain several solutions:** In the former netprologo version (based on a GPJ) it was possible to have multiple prolog instances running at the same time (and therefore multiple active queries). However in the current version it is not possible, as it is just an interface between Netlogo and the local SWI-Prolog installation. That is why some primitives have been added to allow querying and storing multiple solutions, to be read later, before running a new query:
 * **Opens a query and stores N solutions:** `<Solution-storage> run-for-n-solutions <N> <PrologCall-String>`. Opens a new Prolog query, reads and store the first `N` solutions. It returns a reference to the solutions storage.
 * **Advances till next stored solution:** `<Boolean> netprologo:next-stored-solution <Solution-storage>`. Load the next solution of the stored query. It returns `false` if there are no more results to be read. If it returns `true`, this next solution is ready to be read  (see `dereference-stored-var`).
 * **Reads the value of a variable for the last loaded solution from storage:** `<List/String/Number> netprologo:dereference-stored-var <Solution-storage> <Var-name>`. In the current version it can read `Strings`, numbers (`Float` or `Integer`) and `NetLogo lists` made of any of the former elements, including `nested lists`. Thus other prolog data types like `functors` are not actually supported.
