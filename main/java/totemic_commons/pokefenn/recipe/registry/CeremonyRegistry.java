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
    }

    private final ItemStack[] item;
    private final boolean doesNeedItems;
    private final MusicEnum instrument1;
    private final MusicEnum instrument2;
    private final MusicEnum instrument3;
    private final MusicEnum instrument4;
    private final int ceremonyID;
    private final ICeremonyEffect ceremonyEffect;
    private final int maximumTicks;
    private final boolean lastsForever;
    private final MusicEnum preferedMusic;

    public CeremonyRegistry(boolean doesNeedItems, MusicEnum instrument1, MusicEnum instrument2, MusicEnum instrument3, MusicEnum instrument4, int ceremonyID, ICeremonyEffect ceremonyEffect, int maximumTicks, boolean lastsForever, MusicEnum preferedMusic, ItemStack... item)
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
        this.lastsForever = lastsForever;
        this.preferedMusic = preferedMusic;
    }

    public ItemStack[] getItem()
    {
        return this.item;
    }

    public boolean getDoesNeedItems()
    {
        return this.doesNeedItems;
    }

    public MusicEnum getInstrument1()
    {
        return this.instrument1;
    }

    public MusicEnum getInstrument2()
    {
        return this.instrument2;
    }

    public MusicEnum getInstrument3()
    {
        return this.instrument3;
    }

    public MusicEnum getInstrument4()
    {
        return this.instrument4;
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

    public boolean doesLastForever()
    {
        return this.lastsForever;
    }

    public MusicEnum getPreferedMusic()
    {
        return this.preferedMusic;
    }


}
