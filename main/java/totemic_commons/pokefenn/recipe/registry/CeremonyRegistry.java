package totemic_commons.pokefenn.recipe.registry;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.api.plant.PlantEnum;

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
    private final PlantEnum plant1;
    private final PlantEnum plant2;
    private final PlantEnum plant3;
    private final PlantEnum plant4;
    private final int ceremonyID;
    private final ICeremonyEffect ceremonyEffect;
    private final int maximumTicks;
    private final boolean lastsForever;
    private final int costPer5Seconds;
    private final MusicEnum preferedMusic;

    public CeremonyRegistry(boolean doesNeedItems, PlantEnum plant1, PlantEnum plant2, PlantEnum plant3, PlantEnum plant4, int ceremonyID, ICeremonyEffect ceremonyEffect, int maximumTicks, boolean lastsForever, int costPer5Seconds, MusicEnum preferedMusic, ItemStack... item)
    {
        this.item = item;
        this.doesNeedItems = doesNeedItems;
        this.plant1 = plant1;
        this.plant2 = plant2;
        this.plant3 = plant3;
        this.plant4 = plant4;
        this.ceremonyID = ceremonyID;
        this.ceremonyEffect = ceremonyEffect;
        this.maximumTicks = maximumTicks;
        this.lastsForever = lastsForever;
        this.costPer5Seconds = costPer5Seconds;
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

    public PlantEnum getPlant1()
    {
        return this.plant1;
    }

    public PlantEnum getPlant2()
    {
        return this.plant2;
    }

    public PlantEnum getPlant3()
    {
        return this.plant3;
    }

    public PlantEnum getPlant4()
    {
        return this.plant4;
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

    public int getCostPer5Seconds()
    {
        return this.costPer5Seconds;
    }

    public MusicEnum getPreferedMusic()
    {
        return  this.preferedMusic;
    }


}
