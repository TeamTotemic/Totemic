- The Totempedia entries in the Ceremonies category are now locked until the required music instruments are obtained. This should make it less overwhelming for newcomers, as only those Ceremonies will be displayed that are currently performable.
  - The Jingle Dress Totempedia entry will now unlock together with the Wind Chime entry.
  - In case you get erroneously locked Instrument or Ceremony entries after the update, they should unlock once you place the relevant Instruments in your inventory.
- Added the `medicineBagBlacklist` server config option, which allows specifying Totem Carvings that may not be used in Medicine Bags.
- Added a sound when opening, closing or setting the carving of a Medicine Bag.
- Fixed Red Cedar Leaves not being harvestable with modded shears (they can now be harvested with any tool that has the "shears_dig" item ability).
- Fixed a visual glitch where the music bar in the Ceremony HUD could overflow.
- Expanded the KubeJS integration (still experimental):
  - New Music Instruments, Totem Carvings and Ceremonies can now be created via `StartupEvents.registry`, using the registry keys `'totemic:a_instrument'`, `'totemic:c_totem_carving'` and `'totemic:d_ceremony'`, respectively (the letter prefixes are unfortunately necessary to ensure the correct registration order).
  - Added the `TotemicEvents.modifyTotemCarvings` event.
  - The duration of Ceremony effects can now be changed via `TotemicEvents.ceremonyEffectTick`.

API changes:
- Completely refactored TotemCarving and other relevant classes. PortableTotemCarving and MedicineBagEffect have been removed and merged with TotemCarving and TotemEffect, respectively, which greatly simplifies the system.
- Removed TotemEffectEvent and MedicineBagEffectEvent and replaced them with setter methods on TotemCarving.
- Added setter methods for modifying Ceremonies.
- The duration of Ceremony effects can now be changed via CeremonyEvent.EffectTick.