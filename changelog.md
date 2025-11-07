- The Totempedia entries in the Ceremonies category are now locked until the required music instruments are obtained. This should make it less overwhelming for newcomers, as only those Ceremonies will be displayed that are currently performable.
  - The Jingle Dress Totempedia entry will now unlock together with the Wind Chime entry.
  - In case you get erroneously locked Instrument or Ceremony entries after the update, they should unlock once you place the relevant Instruments in your inventory.
- Added the `medicineBagBlacklist` server config option, which allows specifying Totem Carvings that may not be used in Medicine Bags.
- Added a sound when opening, closing or setting the carving of a Medicine Bag.
- Fixed a visual glitch where the music bar in the Ceremony HUD could overflow.
- Added KubeJS integration (documentation pending).
  - It can be used mostly the same as in 1.21.1, but an important difference is that, due to a limitation of KubeJS in 1.20.1, you can't use KubeJS's `StartupEvents.registry` to register Totemic contents. Instead, use `TotemicEvents.registerMusicInstruments`, etc.

API changes: See 1.21.1-0.12.18.