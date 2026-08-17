- Arrows shot from Baykok's Bow now do 25% extra damage (like they did in 1.12.2, I apparently forgot to port that over to the newer versions)
- Added the block tag `totemic:supports_tipi` to specify which blocks Tipis can be placed on
- Fixed Baykok's Bow not shooting tipped arrows when they are used
- Fixed waking up outside the Tipi after sleeping in it
- Fixed Wind Chime congestion particles not being visible immediately
- Fixed Totem Pole breaking particles having the wrong color
- (@Spagles) Fixed a potential crash with the ceremony HUD
- (@Spagles) Small performance improvement for ceremonies that affect blocks, such as the Zaphkiel Waltz
- Large-scope internal changes to facilitate future porting to other mod loaders
- KubeJS integration: Added warning when setting an empty or invalid item for a music instrument
- KubeJS integration: Added an explicit error when setting an incorrect number of selectors for a ceremony
- Now requires at least NeoForge version 21.1.233

API changes:
- The API jar now includes compiled class files in addition to the sources
- Moved the parts of the API that are specific to NeoForge (capabilities, data maps, events) to the subpackage pokefenn.totemic.api.neoforge
- CeremonyInstance and DefaultMusicAcceptor no longer implement INBTSerializable (but the (de)serializeNBT methods are still there)