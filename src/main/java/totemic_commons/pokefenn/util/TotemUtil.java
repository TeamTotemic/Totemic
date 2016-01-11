package totemic_commons.pokefenn.util;

import java.util.Comparator;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import totemic_commons.pokefenn.api.music.MusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 06/02/14
 * Time: 17:07
 */
public class TotemUtil
{
    /**
     * @return a localized representation of the music amount needed to start a ceremony (from "none" up to "crazy large")
     */
    public static String getMusicNeededLocalized(int music)
    {
        String welp = "totemic.musicNeeded.";
        String unlocalized = "";

        if(music == 0)
            unlocalized = "none";
        else if(music <= 120)
            unlocalized = "little";
        else if(music <= 150)
            unlocalized = "littleMedium";
        else if(music <= 180)
            unlocalized = "medium";
        else if(music <= 210)
            unlocalized = "mediumLarge";
        else if(music <= 240)
            unlocalized = "large";
        else
            unlocalized = "crazyLarge";

        return welp + unlocalized;
    }

    private static void setSelectors(TileTotemBase tile, MusicInstrument instr)
    {
        WorldServer world = (WorldServer) tile.getWorld();
        tile.isCeremony = true;

        MusicInstrument[] musicSelectorArray = tile.musicSelector;

        if(musicSelectorArray[0] == null)
        {
            musicSelectorArray[0] = instr;
            musicParticleAtBlocks(world, EnumParticleTypes.NOTE, tile.getPos());
        } else if(musicSelectorArray[1] == null)
        {
            musicSelectorArray[1] = instr;
            musicParticleAtBlocks(world, EnumParticleTypes.NOTE, tile.getPos());
        }
        world.markBlockForUpdate(tile.getPos());
    }

    /**
     * Returns the closest music acceptor from that position, or null if there is none in range
     */
    public static MusicAcceptor getClosestAcceptor(WorldServer world, double x, double y, double z, int horizontalRadius, int verticalRadius)
    {
        return (MusicAcceptor)EntityUtil.getTileEntitiesInRange(world, new BlockPos(x, y, z), horizontalRadius, verticalRadius).stream()
                .filter(te -> te instanceof MusicAcceptor)
                .min(Comparator.comparing(te -> te.getDistanceSq(x, y, z))).orElse(null);
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

        MusicAcceptor tile = getClosestAcceptor((WorldServer) world, x, y ,z, radius, radius);
        if(tile instanceof TileTotemBase && ((TileTotemBase) tile).canMusicSelect())
        {
            setSelectors((TileTotemBase) tile, instr);
        }
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

        MusicAcceptor tile = getClosestAcceptor((WorldServer) world, x, y, z, radius, radius);
        if(tile != null)
        {
            int shiftedMusic = instr.getBaseOutput() + bonusMusicAmount;
            addMusic(tile, instr, shiftedMusic, instr.getMusicMaximum());
        }
    }

    public static void playMusic(World world, BlockPos pos, MusicInstrument instr, int bonusRadius, int bonusMusicAmount)
    {
        playMusic(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, instr, bonusRadius, bonusMusicAmount);
    }

    /**
     * Adds music to the given music acceptor tile entity and spawns particles at its location
     */
    public static void addMusic(MusicAcceptor tile, MusicInstrument instr, int musicAmount, int musicMaximum)
    {
        TileEntity te = (TileEntity) tile;
        WorldServer world = (WorldServer) te.getWorld();

        int added = tile.addMusic(instr, musicAmount);
        if(added > 0)
            musicParticleAtBlocks(world, EnumParticleTypes.NOTE, te.getPos());
        else
            musicParticleAtBlocks(world, EnumParticleTypes.CLOUD, te.getPos());
    }

    /**
     * Sends a packet to the client, spawning a cloud of particles at the given block location
     * @see TotemUtil#particlePacket
     */
    public static void musicParticleAtBlocks(WorldServer world, EnumParticleTypes type, BlockPos pos)
    {
        world.spawnParticle(type, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 6, 0.5, 0.5, 0.5, 0.0);
    }

}
