package totemic_commons.pokefenn.api.recipe;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.api.ceremony.TimeStateEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyActivation
{
    public static List<CeremonyActivation> ceremonyActivation = new ArrayList<CeremonyActivation>();

    private final boolean doesNeedItems;
    private final ItemStack itemStack;
    private final TimeStateEnum timeState;
    private final int maximumTicksForEffect;
    private final int musicNeeded;
    private final int maximumStartupTime;
    private final int melodyPer5After;

    /**
     * @param timeState             If the effect is instant after the ceremony has done its startup, or does it continue on.
     * @param maximumTicksForEffect The maximum amount of ticks the ceremony can do it for, just put 0 if its a instant Ceremony.
     * @param item                  The Item, if you do not need items, just put this as null.
     * @param doesNeedItems         Does it need items for when it starts up?
     * @param musicNeeded           The total amount of musical melody needed for the ceremony.
     * @param maximumStartupTime    The longest it can take for the startup, before the effect starts or anything.
     * @param melodyPer5After       If the ceremony is not instant, how much melody does it take ever 5?
     */

    public CeremonyActivation(boolean doesNeedItems, ItemStack item, TimeStateEnum timeState, int maximumTicksForEffect, int musicNeeded, int maximumStartupTime, int melodyPer5After)
    {
        this.timeState = timeState;
        this.maximumStartupTime = maximumStartupTime;
        this.musicNeeded = musicNeeded;
        this.melodyPer5After = melodyPer5After;
        this.maximumTicksForEffect = maximumTicksForEffect;
        this.doesNeedItems = doesNeedItems;
        this.itemStack = item;
    }

    public CeremonyActivation(TimeStateEnum timeState, int maximumTicksForEffect, int musicNeeded, int maximumStartupTime, int melodyPer5After)
    {
        this.timeState = timeState;
        this.maximumStartupTime = maximumStartupTime;
        this.musicNeeded = musicNeeded;
        this.melodyPer5After = melodyPer5After;
        this.maximumTicksForEffect = maximumTicksForEffect;
        this.doesNeedItems = false;
        this.itemStack = null;
    }

    public CeremonyActivation(TimeStateEnum timeState, int musicNeeded)
    {
        this.timeState = timeState;
        this.musicNeeded = musicNeeded;
        this.itemStack = null;
        this.melodyPer5After = 0;
        this.maximumStartupTime = 0;
        this.doesNeedItems = false;
        this.maximumTicksForEffect = 0;
    }

    public boolean getDoesNeedItems()
    {
        return this.doesNeedItems;
    }

    public ItemStack getItemStack()
    {
        return this.itemStack;
    }

    public TimeStateEnum getTimeState()
    {
        return this.timeState;
    }

    public int getMaximumTicksForEffect()
    {
        return this.maximumTicksForEffect;
    }

    public int getMusicNeeded()
    {
        return this.musicNeeded;
    }

    public int getMaximumStartupTime()
    {
        return this.maximumStartupTime;
    }

    public int getMelodyPer5After()
    {
        return this.melodyPer5After;
    }
}
