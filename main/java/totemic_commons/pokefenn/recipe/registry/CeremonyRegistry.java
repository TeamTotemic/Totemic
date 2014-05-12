package totemic_commons.pokefenn.recipe.registry;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.api.music.MusicEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyRegistry
{
    public static List<CeremonyRegistry> ceremonyRegistry = new ArrayList<CeremonyRegistry>();

    public static void addRecipes()
    {
        //ceremonyRegistry.add(new CeremonyRegistry(false));
    }

    private final ItemStack item;
    private final boolean doesNeedItems;
    private final MusicEnum[] instruments;
    private final int ceremonyID;
    private final ICeremonyEffect ceremonyEffect;
    private final int maximumTicks;
    private final MusicEnum preferedMusic;
    private final boolean isInstant;
    private final int musicNeeded;

    public CeremonyRegistry(boolean doesNeedItems, MusicEnum[] instruments, int ceremonyID, ICeremonyEffect ceremonyEffect, boolean isInstant, int maximumTicks, boolean lastsForever, MusicEnum preferedMusic, ItemStack item, int musicNeeded)
    {
        this.item = item;
        this.doesNeedItems = doesNeedItems;
        this.instruments = instruments;
        this.ceremonyID = ceremonyID;
        this.ceremonyEffect = ceremonyEffect;
        this.maximumTicks = maximumTicks;
        this.preferedMusic = preferedMusic;
        this.isInstant = isInstant;
        this.musicNeeded = musicNeeded;
    }

    public ItemStack getItem()
    {
        return this.item;
    }

    public boolean getDoesNeedItems()
    {
        return this.doesNeedItems;
    }

    public MusicEnum[] getInstruments()
    {
        return this.instruments;
    }

    public int getCeremonyID()
    {
        return this.ceremonyID;
    }

    public ICeremonyEffect getCeremonyEffect()
    {
        return this.ceremonyEffect;
    }

    public int getMaximumTicks()
    {
        return this.maximumTicks;
    }

    public boolean getIsInstant()
    {
        return this.isInstant;
    }

    public MusicEnum getPreferedMusic()
    {
        return this.preferedMusic;
    }

    public int getMusicNeeded()
    {
        return this.musicNeeded;
    }

}
