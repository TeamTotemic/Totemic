package totemic_commons.pokefenn.util;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import totemic_commons.pokefenn.api.music.MusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 06/02/14
 * Time: 17:07
 */
public class TotemUtil
{

    public static String getMusicNeeded(int music)
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

    public static void addPotionEffects(EntityPlayer player, int defaultTime, Potion potion, int defaultStrength, int totemWoodBonus, int repetitionBonus, int melodyAmount)
    {
        Random random = player.getRNG();

        player.addPotionEffect(new PotionEffect(potion.getId(),
                defaultTime + (repetitionBonus * 10) + random.nextInt(41) + melodyAmount + (totemWoodBonus * 10),
                defaultStrength + (repetitionBonus >= 5 || melodyAmount > 112 ? 1 : 0), true));
    }

    public static void addNegativePotionEffect(EntityPlayer player, int defaultTime, Potion potion, int defaultStrength, int totemWoodBonus, int repetitionBonus, int melodyAmount)
    {
        player.addPotionEffect(new PotionEffect(potion.id,
                defaultTime - (totemWoodBonus * 8) - (repetitionBonus * 7) - (melodyAmount / 32),
                defaultStrength - (melodyAmount > 112 ? 1 : 0), true));
    }

    public static void setSelectors(TileTotemBase tile, MusicInstrument instr)
    {
        WorldServer world = (WorldServer)tile.getWorldObj();
        tile.isCeremony = true;

        MusicInstrument[] musicSelectorArray = tile.musicSelector;

        if(musicSelectorArray[0] == null)
        {
            musicSelectorArray[0] = instr;
            musicParticleAtBlocks(world, tile.xCoord, tile.yCoord, tile.zCoord, "note");
        } else if(musicSelectorArray[1] == null)
        {
            musicSelectorArray[1] = instr;
            musicParticleAtBlocks(world, tile.xCoord, tile.yCoord, tile.zCoord, "note");
        }
        world.markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
    }

    public static void playMusicFromItemForCeremonySelector(EntityPlayer player, int x, int y, int z, MusicInstrument instr, int bonusRadius)
    {
        World world = player.worldObj;
        int radius = instr.getBaseRange() + bonusRadius;

        for(TileEntity tile: EntityUtil.getTileEntitiesInRange((WorldServer)world, x, y, z, radius, radius))
        {
            if(tile instanceof TileTotemBase && ((TileTotemBase) tile).canMusicSelect())
            {
                setSelectors((TileTotemBase) tile, instr);
                return;
            }
        }
    }

    public static void playMusicFromBlockForCeremonySelector(World world, int x, int y, int z, MusicInstrument instr, int bonusRadius)
    {
        int radius = instr.getBaseRange() + bonusRadius;

        for(TileEntity tile: EntityUtil.getTileEntitiesInRange((WorldServer)world, x, y, z, radius, radius))
        {
            if(tile instanceof TileTotemBase && ((TileTotemBase) tile).canMusicSelect())
            {
                setSelectors((TileTotemBase) tile, instr);
                return;
            }
        }
    }

    public static void playMusicForCeremony(TileTotemic tileInstr, MusicInstrument instr, int bonusRadius, int bonusMusicAmount)
    {
        World world = tileInstr.getWorldObj();

        int x = tileInstr.xCoord;
        int y = tileInstr.yCoord;
        int z = tileInstr.zCoord;

        int radius = bonusRadius + instr.getBaseRange();

        for(TileEntity tile: EntityUtil.getTileEntitiesInRange((WorldServer)world, x, y, z, radius, radius))
        {
            if(tile instanceof MusicAcceptor)
            {
                int shiftedMusic = instr.getBaseOutput() + bonusMusicAmount;

                addMusic((MusicAcceptor)tile, instr, shiftedMusic, instr.getMusicMaximum());
                return;
            }
        }
    }

    public static void playMusicFromItem(World world, int x, int y, int z, MusicInstrument instr, int bonusRadius, int bonusMusicAmount)
    {
        int radius = instr.getBaseRange() + bonusRadius;

        for(TileEntity tile: EntityUtil.getTileEntitiesInRange((WorldServer)world, x, y, z, radius, radius))
        {
            if(tile instanceof MusicAcceptor)
            {
                int shiftedMusic = instr.getBaseOutput() + bonusMusicAmount;

                addMusic((MusicAcceptor)tile, instr, shiftedMusic, instr.getMusicMaximum());
                return;
            }
        }
    }

    public static void addMusic(MusicAcceptor tile, MusicInstrument instr, int musicAmount, int musicMaximum)
    {
        TileEntity te = (TileEntity)tile;
        WorldServer world = (WorldServer)te.getWorldObj();
        int x = te.xCoord, y = te.yCoord, z = te.zCoord;

        int added = tile.addMusic(instr, musicAmount);
        if(added > 0)
            musicParticleAtBlocks(world, x, y, z, "note");
        else
            musicParticleAtBlocks(world, x, y, z, "cloud");
    }

    /**
     * Sends a packet to the client, spawning a cloud of particles
     * @param world the world. Must be an instance of WorldServer.
     * @param name the name of the particle
     * @param x the x-position
     * @param y the y-position
     * @param z the z-position
     * @param num how many particles to spawn. Can also be zero, then only one
     * particle is spawned and the next three parameters give the velocity rather than the spread.
     * @param spreadX how much the cloud is spread out in x-direction
     * @param spreadY how much the cloud is spread out in y-direction
     * @param spreadZ how much the cloud is spread out in z-direction
     * @param vel the velocity of the particles
     */
    public static void particlePacket(World world, String name, double x, double y, double z, int num,
            double spreadX, double spreadY, double spreadZ, double vel)
    {
        ((WorldServer) world).func_147487_a(name, x, y, z, num, spreadX, spreadY, spreadZ, vel);
    }

    public static void musicParticleAtBlocks(WorldServer world, int xCoord, int yCoord, int zCoord, String particle)
    {
        particlePacket(world, particle, xCoord + 0.5, yCoord, zCoord + 0.5, 6, 0.5, 0.5, 0.5, 0.0);
    }


}
