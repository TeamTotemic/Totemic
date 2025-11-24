Totemic for Minecraft 1.7.10
=======

A magical themed Minecraft mod with a focus on Totem Poles, nature and the power of music.

This branch backports some of the features from newer versions of the mod to the 1.7.10 version, using [RetroFuturaGradle](https://github.com/GTNewHorizons/RetroFuturaGradle) to enable building for 1.7.10 in modern times.

## Building and running
To build the mod, run `./gradlew build` (on Windows, replace `./gradlew` with `gradlew.bat` in each case).

You can run the game from the terminal with `./gradlew runClient` or `./gradlew runServer`.

### Setting up a development environment
#### IDEA
Run `./gradlew setupDecompWorkspace` and import the project in IDEA. Should work without any problems, but I haven't tested it.

#### VS Code / Eclipse
Run `./gradlew setupDecompWorkspace eclipse` and open the workspace folder in VS Code or import the Gradle project in Eclipse.

Unfortunately, RetroFuturaGradle doesn't fully support IDEs other than IDEA. In particular, the game can't be launched natively from VS Code or Eclipse but only via the 'runClient' and 'runServer' Gradle tasks (also, the VS Code Gradle extension currently seems to have a bug where running those tasks in debug mode doesn't work reliably).
