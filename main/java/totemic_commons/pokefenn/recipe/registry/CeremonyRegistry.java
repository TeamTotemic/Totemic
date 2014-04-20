package totemic_commons.pokefenn.recipe.registry;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.ceremony.CeremonyHarvestFeast;
import totemic_commons.pokefenn.ceremony.CeremonyNight;
import totemic_commons.pokefenn.ceremony.CeremonyRain;
import totemic_commons.pokefenn.lib.PlantIds;

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
        int wheat = PlantIds.WHEAT_ID;
        int moonglow = PlantIds.MOONGLOW_ID;
        int carrot = PlantIds.CARROT_ID;
        int melon = PlantIds.MELON_ID;
        int bloodwart = PlantIds.BLOODWART_ID;
        int fungus = PlantIds.FUNGUS_ID;
        int lotus = PlantIds.LOTUS_ID;
        int potato = PlantIds.POTATO_ID;

        ceremonyRegistry.add(new CeremonyRegistry(false, null, moonglow, moonglow, moonglow, moonglow, 75, moonglow, 1, new CeremonyNight(), 200, 20 * 30, false, 0));
        ceremonyRegistry.add(new CeremonyRegistry(false, null, lotus, lotus, lotus, lotus, 50, lotus, 2, new CeremonyRain(), 200, 20 * 30, false, 0));
        ceremonyRegistry.add(new CeremonyRegistry(false, null, wheat, carrot, wheat, potato, 75, wheat, 3, new CeremonyHarvestFeast(), 150, 20 * 1000, true, 50));
        //ceremonyRegistry.add(new CeremonyRegistry(false, null, bloodwart, bloodwart, bloodwart, bloodwart, 40, new CeremonyDrought(), 200, 20 * 30));
    }

    /*
     * Item is the Itemstack, that is optional to start, does need items referes to that itemstack, the 4 plants are the plants that have to be in the world for the effect. Percentage needed is how much of a certain plant is needed, and then ext one is which plant.
     * Ceremony ID is the id of the ceremony, dont let it conflict. Next is the actual effect, it just needs to implement ICeremonyEffect, overallDrain is the total of plant essence it needs, and maximum ticks is how long it has to drain before it starts.
     * Lasts forever is obvious, and costPer5Seconds is only needed if you use that, and its how much it drains, every 5 seconds.
     */

    private final ItemStack item;
    private final boolean doesNeedItems;
    private final int plant1;
    private final int plant2;
    private final int plant3;
    private final int plant4;
    private final int percentageNeeded;
    private final int plantForPercentage;
    private final int ceremonyID;
    private final ICeremonyEffect ceremonyEffect;
    private final int overallDrain;
    private final int maximumTicks;
    private final boolean lastsForever;
    private final int costPer5Seconds;

    public CeremonyRegistry(boolean doesNeedItems, ItemStack item, int plant1, int plant2, int plant3, int plant4, int percentageNeeded, int plantForPercentage, int ceremonyID, ICeremonyEffect ceremonyEffect, int overallDrain, int maximumTicks, boolean lastsForever, int costPer5Seconds)
    {
        this.item = item;
        this.doesNeedItems = doesNeedItems;
        this.plant1 = plant1;
        this.plant2 = plant2;
        this.plant3 = plant3;
        this.plant4 = plant4;
        this.percentageNeeded = percentageNeeded;
        this.plantForPercentage = plantForPercentage;
        this.ceremonyID = ceremonyID;
        this.ceremonyEffect = ceremonyEffect;
        this.overallDrain = overallDrain;
        this.maximumTicks = maximumTicks;
        this.lastsForever = lastsForever;
        this.costPer5Seconds = costPer5Seconds;
    }

    public ItemStack getItem()
    {
        return this.item;
    }

    public boolean getDoesNeedItems()
    {
        return this.doesNeedItems;
    }

    public int getPlant1()
    {
        return this.plant1;
    }

    public int getPlant2()
    {
        return this.plant2;
    }

    public int getPlant3()
    {
        return this.plant3;
    }

    public int getPlant4()
    {
        return this.plant4;
    }

    public int getPercentage()
    {
        return this.percentageNeeded;
    }

    public int getPlantForPercentage()
    {
        return this.plantForPercentage;
    }

    public int getCeremonyID()
    {
        return this.ceremonyID;
    }

    public ICeremonyEffect getCeremonyEffect()
    {
        return this.ceremonyEffect;
    }

    public int getOverallDrain()
    {
        return this.overallDrain;
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

}
