package totemic_commons.pokefenn.api.music;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class MusicHandler
{

    public static List<MusicHandler> musicHandler = new ArrayList<MusicHandler>();

    private final ItemStack[] itemStacks;
    private final int baseOutput;
    private final int musicMaximum;
    private final int musicId;
    private final int baseRange;

    public MusicHandler(int musicId, ItemStack[] items, int baseOutput, int baseRange, int musicMaximum)
    {
        this.musicId = musicId;
        this.itemStacks = items;
        this.baseOutput = baseOutput;
        this.musicMaximum = musicMaximum;
        this.baseRange = baseRange;
    }

    public int getMusicId()
    {
        return this.musicId;
    }

    public ItemStack[] getItems()
    {
        return this.itemStacks;
    }

    public ItemStack getItem(int i)
    {
        return this.itemStacks[i];
    }

    public int getMusicMaximum()
    {
        return this.musicMaximum;
    }

    public int getBaseOutput()
    {
        return this.baseOutput;
    }

    public int getBaseRange()
    {
        return this.baseRange;
    }

}
