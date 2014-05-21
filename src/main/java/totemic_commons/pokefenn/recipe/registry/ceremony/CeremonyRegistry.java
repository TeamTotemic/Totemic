package totemic_commons.pokefenn.recipe.registry.ceremony;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.ceremony.CeremonyCrafting;
import totemic_commons.pokefenn.ceremony.CeremonyFlowingTime;

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
        ceremonyRegistry.add(new CeremonyRegistry(false, MusicEnum.FLUTE_MUSIC, MusicEnum.FLUTE_MUSIC, MusicEnum.FLUTE_MUSIC, MusicEnum.FLUTE_MUSIC, 1, new CeremonyFlowingTime(), false, 20 * 30, MusicEnum.FLUTE_MUSIC, null, 100, 60 * 20, 0));
        ceremonyRegistry.add(new CeremonyRegistry(false, MusicEnum.DRUM_MUSIC, MusicEnum.DRUM_MUSIC, MusicEnum.FLUTE_MUSIC, MusicEnum.FLUTE_MUSIC, 2, new CeremonyCrafting(), false, (20 * 60) * 5, MusicEnum.FLUTE_MUSIC, null, 100, 60 * 20, 0));
    }

    private final ItemStack item;
    private final boolean doesNeedItems;
    private final MusicEnum instrument1;
    private final MusicEnum instrument2;
    private final MusicEnum instrument3;
    private final MusicEnum instrument4;
    private final int ceremonyID;
    private final ICeremonyEffect ceremonyEffect;
    private final int maximumTicks;
    private final MusicEnum preferedMusic;
    private final boolean isInstant;
    private final int musicNeeded;
    private final int maximumStartupTime;
    private final int melodyPer5After;

    public CeremonyRegistry(boolean doesNeedItems, MusicEnum instrument1, MusicEnum instrument2, MusicEnum instrument3, MusicEnum instrument4, int ceremonyID, ICeremonyEffect ceremonyEffect, boolean isInstant, int maximumTicks, MusicEnum preferedMusic, ItemStack item, int musicNeeded, int maximumStartupTime, int melodyPer5After)
    {
        this.item = item;
        this.doesNeedItems = doesNeedItems;
        this.instrument1 = instrument1;
        this.instrument2 = instrument2;
        this.instrument3 = instrument3;
        this.instrument4 = instrument4;
        this.ceremonyID = ceremonyID;
        this.ceremonyEffect = ceremonyEffect;
        this.maximumTicks = maximumTicks;
        this.preferedMusic = preferedMusic;
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

    public int getMaximumStartupTime()
    {
        return this.maximumStartupTime;
    }

}
