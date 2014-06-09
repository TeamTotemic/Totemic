package totemic_commons.pokefenn.api.recipe;

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

    private final ItemStack item;
    private final boolean doesNeedItems;
    private final MusicEnum instrument1;
    private final MusicEnum instrument2;
    private final MusicEnum instrument3;
    private final MusicEnum instrument4;
    private final int ceremonyID;
    private final ICeremonyEffect ceremonyEffect;
    private final int maximumTicks;
    private final boolean isInstant;
    private final int musicNeeded;
    private final int maximumStartupTime;
    private final int melodyPer5After;
    private final String name;

    public CeremonyRegistry(String name, boolean doesNeedItems, MusicEnum instrument1, MusicEnum instrument2, MusicEnum instrument3, MusicEnum instrument4, int ceremonyID, ICeremonyEffect ceremonyEffect, boolean isInstant, int maximumTicks, ItemStack item, int musicNeeded, int maximumStartupTime, int melodyPer5After)
    {
        this.name = name;
        this.item = item;
        this.doesNeedItems = doesNeedItems;
        this.instrument1 = instrument1;
        this.instrument2 = instrument2;
        this.instrument3 = instrument3;
        this.instrument4 = instrument4;
        this.ceremonyID = ceremonyID;
        this.ceremonyEffect = ceremonyEffect;
        this.maximumTicks = maximumTicks;
        this.isInstant = isInstant;
        this.musicNeeded = musicNeeded;
        this.maximumStartupTime = maximumStartupTime;
        this.melodyPer5After = melodyPer5After;
    }

    public ItemStack getItem()
    {
        return this.item;
    }

    public boolean getDoesNeedItems()
    {
        return this.doesNeedItems;
    }

    public MusicEnum getInstruments(int i)
    {
        if(i == 0)
            return instrument1;
        if(i == 1)
            return instrument2;
        if(i == 2)
            return instrument3;
        if(i == 3)
            return instrument4;

        return null;
    }

    public int getMelodyPer5After()
    {
        return this.melodyPer5After;
    }

    public int getCeremonyID()
    {
        return this.ceremonyID;
    }

    public ICeremonyEffect getCeremonyEffect()
    {
        return this.ceremonyEffect;
    }

    public int getMaximumTicksOnEffect()
    {
        return this.maximumTicks;
    }

    public boolean getIsInstant()
    {
        return this.isInstant;
    }

    public int getMusicNeeded()
    {
        return this.musicNeeded;
    }

    public int getMaximumStartupTime()
    {
        return this.maximumStartupTime;
    }

    public String getName()
    {
        return this.name;
    }

}
