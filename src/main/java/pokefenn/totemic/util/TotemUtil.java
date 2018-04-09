package pokefenn.totemic.util;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;

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
     * Adds music to the given music acceptor tile entity and spawns particles at its location
     */
    @Deprecated
    public static void addMusic(MusicAcceptor tile, @Nullable Entity entity, MusicInstrument instr, int musicAmount)
    {
        TileEntity te = (TileEntity) tile;
        WorldServer world = (WorldServer) te.getWorld();
        BlockPos pos = te.getPos();

        if(tile.addMusic(instr, musicAmount))
            world.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 6, 0.5, 0.5, 0.5, 0.0);
        else
            world.spawnParticle(EnumParticleTypes.CLOUD, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 6, 0.5, 0.5, 0.5, 0.0);
    }

    /**
     * Plays a sound at the given location
     */
    public static void playSound(World world, BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch)
    {
        world.playSound(null, pos, sound, category, volume, pitch);
    }
}
