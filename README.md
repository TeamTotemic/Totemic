# Totemic

A magical themed Minecraft mod with a focus on Totem Poles, nature and the power of music.

For downloads and more information see on [CurseForge](https://www.curseforge.com/minecraft/mc-mods/totemic) or [Modrinth](https://modrinth.com/mod/fenns_totemic).

## Reporting issues:
Please mention the Minecraft and mod version that you are using.
If the issue is a crash, make sure to provide the log file or crash report.
Try to describe the issue as precisely as possible, if possible give steps to reproduce it. Depending on the situation, pictures can be helpful.

## Pull requests:
New translations or improvements to existing translations are welcome. The language files can be found in the folder [common/src/main/resources/assets/totemic/lang](common/src/main/resources/assets/totemic/lang).

For code or content PRs: Please go over all major changes with me (on Discord or by opening an issue) before submitting them, I don't want anything to go to waste :(
Try to place comments in anything that is not obvious, it can be confusing to understand others' code!

## Setting up a workspace/building from source:
The mod uses a multiloader setup (although currently only NeoForge is supported) inspired by jaredlll08's MultiLoader Template and the setup of other multiloader mods such as Botania.

You can import the root folder as Gradle project into an IDE of your choice (should work with IDEA, Eclipse and VS Code). Run `./gradlew build` or `gradlew.bat build` to build the mod. The built jar can be found in neoforge/build/libs.

## Credits:
- The textures were mainly designed by nojustgavin, Sunconure11 and VincentLongiug.
- The models for the Totem Poles were designed by Nefilto.
- The Flute sounds were cut from [overblow.mp3](https://freesound.org/people/kerri/sounds/37144/) by Kerri Lake,
used under [CC BY 3.0](https://creativecommons.org/licenses/by/3.0/), slightly changed in pitch.
- The Eagle-Bone Whistle sounds were cut from [this sample](https://freesound.org/people/Petrucio/sounds/276977/)
by SpawnofSirius.
- Thanks also to ZestyBlaze, who is working on a Fabric port.
