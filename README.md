# packwiz Companion Mod
Hi! This is the building site for a companion mod for packwiz. Nothing to see here yet, but here's what is planned in the future, ordered by most to least likely/easy:

- Integrate with Mod Menu to indicate which mods are managed by packwiz
  - Forge support is not planned, but contributions are welcomed!
- Add checks to suggest users update when the pack version is out of sync with a server that also has the mod
- Run packwiz-installer from within the companion mod, using loader plugins (Quilt/Forge only)
  - This will allow packwiz-installer to be used in more situations (but has some limitations - the companion mod cannot easily be updated by itself, and the loader cannot be updated by packwiz-installer in this state)
- Allow disabling/enabling mods from within the game (requiring a restart to apply the change)
- Allow installing and removing mods from within the game