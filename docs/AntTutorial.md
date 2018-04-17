#Ant Tutorial

##Quick-start(TLDR):

1. [Go here](http://ant.apache.org/manual/install.html) and follow the instructions.
2. [Go here](https://github.com/JetBrains/kotlin/releases/tag/v1.2.31) and install the kotlin compiler to c:\kotlin (so the complete path should be c:\kotlin\kotlinc\).
3. In the same folder as build.xml, run "ant" in the commandline to compile the project.

NOTE: You can put the compiler somewhere else, but this is the default path in the buildfile and thus you would have to change the build.xml file to reflect that. 

To do this, open the build.xml file and find the line <property name="kotlin.lib"  value="C:\Kotlin\kotlinc\lib"/>, then change the value to reflect your path.


##Installing Ant
To be added later.

##Installing the kotlin standalone compiler
To be added later.

##How to use Ant

To use Ant, simply type "ant" in the commandline while in the same directory as the build.xml file and the default command(compile) will be ran.

To run another command than the default, type "ant <command>" 

##Commands

The default command is "compile", which compiles all the java and kotlin source files in the src dir.

Other commands are:

- "clean" This deletes the "build" directory where compiled java and kotlin sources are stored.

- "jar" NOT YET IMPLEMENTED this will make a runnable .jar file of the project. The .jar file will be placed in "build/jar".

- "run" NOT YET IMPLEMENTED this will run the .jar file.

- "clean-compile" runs the "clean" command and then the "compile" command.

- "main" this isn't a command per-say, instead this is the default command that gets called when no other is specified, currently runs the "compile" command.
