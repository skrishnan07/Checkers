Read Me for Running Checkers

Installing and running the program
------------------------------------

Checkers is a Java application developed using NetBeans 8.0.2.  it is built for the Java 1.8 runtime
the Checkers.jar file included under the dist folder in the distribution.

There are a couple of "batch" files included to run the application in a Windows or UNIX/Linux environment.

Use "checkers.bat" on a Windows machine.
From a UNIX/MacOS/Linux command shell, simply type "/bin/sh runCheckers.sh" or an equivalent command.

Alternatively, the project can be executed from within NetBeans. A Netbeans project and all required source files are also included.


Playing this version of the Game
---------------------------------

Checkers is a lite version of the American Checkers or English Draughts game.
The Game does not allow pieces to be promoted.  A single player can play against the computer.
The game ends either when all pieces of a player are removed from the board or if there is a stalemate for one of the players

The user interacts with the application using a simple command line interface.
User inputs are guided and limited to menu choices. The user can quit the game at any time.

Board status is displayed using a simple character matrix


Some Information on time spent on this project
-----------------------------------------------

I spent a cummulative total of 6 hours on this project not counting breaks.
Here is a rough break up of time spent on the project:

1.  1 hour designing the classes and refactoring
2.  2.5 hours coding and refactoring
3   1 hour debugging the code ( There was one particular coding error in the Player class that took me a while to isolate and weed out)
4.  1 hour testing and cleaning up user prompts and board display
5.  0.5 hrs setting up the deployment image and this documentation

If somebody can do this is 1-4 hours, hats off to them. I tried my best to compress my effort into the allotted 4 hours.
If I had more time, I had plans to add a simple GUI, or attempt to port this to C# and add more in-line commenting.

The choice of Java as a development language was largely because of the IDEs I had in place and my general proficiency in it compared to other languages
