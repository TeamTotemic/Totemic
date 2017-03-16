package totemic_commons.pokefenn.util;

import java.util.Comparator;
import java.util.Optional;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import totemic_commons.pokefenn.api.music.MusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

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
        else if(music <= 120)
            amount = "little";
        else if(music <= 150)
            amount = "littleMedium";
        else if(music <= 180)
            amount = "medium";
        else if(music <= 210)
            amount = "mediumLarge";
        else if(music <= 240)
            amount = "large";
        else
            amount = "crazyLarge";

        return "totemic.musicNeeded." + amount;
    }

    /**
     * Returns the closest music acceptor from that position, if there is any in range
     */
    public static Optional<MusicAcceptor> getClosestAcceptor(WorldServer world, double x, double y, double z, int horizontalRadius, int verticalRadius)
    {
        return EntityUtil.getTileEntitiesInRange(TileEntity.class, world, new BlockPos(x, y, z), horizontalRadius, verticalRadius).stream()
                .filter(te -> te instanceof MusicAcceptor)
                .min(Comparator.comparing(te -> te.getDistanceSq(x, y, z)))
                .map(te -> (MusicAcceptor) te);
    }

    /**
     * Plays music at the given position to nearby Totem bases to select a ceremony.
     * Usually this is triggered by playing the instrument while sneaking.
     * @param instr the instrument
     * @param bonusRadius additional radius
     */
    public static void playMusicForSelector(World world, double x, double y, double z, MusicInstrument instr, int bonusRadius)
    {
        int radius = instr.getBaseRange() + bonusRadius;

        getClosestAcceptor((WorldServer) world, x, y ,z, radius, radius)
            .filter(tile -> tile instanceof TileTotemBase && ((TileTotemBase) tile).canSelect())
            .ifPresent(tile -> ((TileTotemBase) tile).addSelector(instr));
    }

    public static void playMusicForSelector(World world, BlockPos pos, MusicInstrument instr, int bonusRadius)
    {
        playMusicForSelector(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, instr, bonusRadius);
    }

    /**
     * Plays music at the given position to nearby music acceptors
     * @param instr the instrument
     * @param bonusRadius additional radius
     * @param bonusMusicAmount additional music amount
     */
    public static void playMusic(World world, double x, double y, double z, MusicInstrument instr, int bonusRadius, int bonusMusicAmount)
    {
        int radius = instr.getBaseRange() + bonusRadius;

        getClosestAcceptor((WorldServer) world, x, y, z, radius, radius)
            .ifPresent(tile -> {
                int shiftedMusic = instr.getBaseOutput() + bonusMusicAmount;
                addMusic(tile, instr, shiftedMusic);
            });
    }

    /**
     * Plays music at the given position to nearby music acceptors
     * @param instr the instrument
     * @param bonusRadius additional radius
     * @param bonusMusicAmount additional music amount
     */
    public static void playMusic(World world, BlockPos pos, MusicInstrument instr, int bonusRadius, int bonusMusicAmount)
    {
        playMusic(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, instr, bonusRadius, bonusMusicAmount);
    }

    /**
     * Adds music to the given music acceptor tile entity and spawns particles at its location
     */
    public static void addMusic(MusicAcceptor tile, MusicInstrument instr, int musicAmount)
    {
        TileEntity te = (TileEntity) tile;
        WorldServer world = (WorldServer) te.getWorld();
        BlockPos pos = te.getPos();

        if(tile.addMusic(instr, musicAmount))
            world.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 6, 0.5, 0.5, 0.5, 0.0);
        else
            world.spawnParticle(EnumParticleTypes.CLOUD, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 6, 0.5, 0.5, 0.5, 0.0);
    }

    /**
     * Plays a sound at the entity's location. If the entity is a player, they will also hear it.
     */
    public static void playSound(Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch)
    {
        //Can't use entity.playSound here, otherwise if entity is a player they will hear nothing
        entity.worldObj.playSound(null, entity.posX, entity.posY, entity.posZ, sound, category, volume, pitch);
    }

    /**
     * Plays a sound at the given location
     */
    public static void playSound(World world, BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch)
    {
        world.playSound(null, pos, sound, category, volume, pitch);
    }
}
