package pokefenn.totemic.util;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TotemUtil
{
    /**
     * @return an unlocalized string representation of the music amount needed
     * to start a ceremony (from "none" up to "crazy large")
     */
    public static String getMusicNeeded(int music)
    {
        String amount;

        if(music == 0)
            amount = "none";
        else if(music <= 110)
            amount = "little";
        else if(music <= 150)
            amount = "littleMedium";
        else if(music <= 195)
            amount = "medium";
        else if(music <= 240)
            amount = "mediumLarge";
        else if(music <= 290)
            amount = "large";
        else
            amount = "crazyLarge";

        return "totemic.musicNeeded." + amount;
    }

    /**
     * Plays a sound at the given location
     */
    public static void playSound(World world, BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch)
    {
        world.playSound(null, pos, sound, category, volume, pitch);
    }
}
