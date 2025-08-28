The ability to adjust Music Instruments, Ceremonies and other contents has been requested for a long time, and now we are finally delivering on that by adding integration with KubeJS. This is still experimental, and feedback and suggestions are much appreciated.

Currently, Totemic provides the startup events `TotemicEvents.modifyMusicInstruments` and `modifyCeremonies`, as well as several server and client events for hooking into Ceremonies, e.g. `ceremonyEffectTick`.

There is no documentation yet, but for now you can look at the [test scripts](https://github.com/TeamTotemic/Totemic/tree/1.21/KubeJS-Test-Scripts) to see how the events can be used.

Changing Totem Effects is not yet supported since I plan on making major changes to the code first to resolve some problems.

Further changes:
- Added the config options `ceremonyStartupTimeMultiplierEasy`, `...Normal` and `...Hard`, which allow simple adjustment of Ceremony startup times for each difficulty without Kube
  - These options will appear in "totemic-server.toml" when you load a world or start the server
- (@HanJiang-cn) Updated Simplified Chinese translation