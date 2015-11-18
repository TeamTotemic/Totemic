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
        else if(music < 50)
            unlocalized = "little";
        else if(music < 75)
            unlocalized = "littleMedium";
        else if(music < 100)
            unlocalized = "medium";
        else if(music < 125)
            unlocalized = "mediumLarge";
        else if(music < 150)
            unlocalized = "large";
        else
            unlocalized = "crazyLarge";

        return welp + unlocalized;
    }

    public static void addPotionEffects(EntityPlayer player, int defaultTime, Potion potion, int defaultStrength, int totemWoodBonus, int repetitionBonus, int melodyAmount)
    {
        Random random = new Random();

        player.addPotionEffect(new PotionEffect(potion.getId(), defaultTime + (repetitionBonus * 10) + (random.nextInt(6) * 8) + melodyAmount + (totemWoodBonus * 10), defaultStrength + (repetitionBonus == 5 || melodyAmount > 112 ? 1 : 0), true));
    }

    public static void addNegativePotionEffect(EntityPlayer player, int defaultTime, Potion potion, int defaultStrength, int totemWoodBonus, int repetitionBonus, int melodyAmount)
    {
        player.addPotionEffect(new PotionEffect(potion.id, defaultTime - (totemWoodBonus * 8) - (repetitionBonus * 7) - (melodyAmount / 32), defaultStrength - (melodyAmount > 112 ? 1 : 0), true));
    }

    public static void playMusicFromItemForCeremonySelector(EntityPlayer player, int x, int y, int z, MusicInstrument instr, int bonusRadius)
    {
        World world = player.worldObj;
        int radius = instr.getBaseRange() + bonusRadius;

        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    if(world.getBlock(x + i, y + j, z + k) != null)
                    {
                        TileEntity tile = world.getTileEntity(x + i, y + j, z + k);
                        if(tile instanceof TileTotemBase && ((TileTotemBase) tile).isMusicSelecting)
                        {
                            setSelectors((TileTotemBase) tile, instr);
                            return;
                        }
                    }
                }
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

    public static void playMusicFromBlockForCeremonySelector(World world, int x, int y, int z, MusicInstrument instr, int bonusRadius)
    {
        int radius = instr.getBaseRange() + bonusRadius;
        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    if(world.getBlock(x + i, y + j, z + k) != null)
                    {
                        TileEntity tile = world.getTileEntity(x + i, y + j, z + k);
                        if(tile instanceof TileTotemBase && ((TileTotemBase) tile).isMusicSelecting)
                        {
                            setSelectors((TileTotemBase) tile, instr);
                            return;
                        }
                    }
                }
    }

    public static void playMusicForCeremony(TileTotemic tileCeremony, MusicInstrument instr, int bonusRadius, int bonusMusicAmount)
    {
        World world = tileCeremony.getWorldObj();

        int x = tileCeremony.xCoord;
        int y = tileCeremony.yCoord;
        int z = tileCeremony.zCoord;

        int radius = bonusRadius + instr.getBaseRange();
        int musicAmount = instr.getBaseOutput() + bonusMusicAmount;

        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    if(world.getBlock(x + i, y + j, z + k) != null)
                    {
                        TileEntity block = world.getTileEntity(x + i, y + j, z + k);

                        if(block instanceof TileTotemBase)
                        {
                            int shiftedMusic = getShiftedMusic(musicAmount, (TileTotemBase) block, instr);

                            playMusic(x, y, z, block, instr, shiftedMusic, instr.getMusicMaximum());
                            return;
                        }

                    }
                }
    }

    public static void playMusic(int x, int y, int z, TileEntity tileEntity, MusicInstrument instr, int musicAmount, int musicMaximum)
    {
        WorldServer world = (WorldServer)tileEntity.getWorldObj();
        int tileX = tileEntity.xCoord, tileY = tileEntity.yCoord, tileZ = tileEntity.zCoord;

        MusicAcceptor tile = (MusicAcceptor)tileEntity;
        int added = tile.addMusic(instr, musicAmount);
        if(added > 0)
            musicParticleAtBlocks(world, tileX, tileY, tileZ, "note");
        else
            musicParticleAtBlocks(world, tileX, tileY, tileZ, "cloud");
    }

    public static void playMusicFromItem(World world, int x, int y, int z, MusicInstrument instr, int bonusRadius, int bonusMusicAmount)
    {
        int radius = instr.getBaseRange() + bonusRadius;
        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    TileEntity block = world.getTileEntity(x + i, y + j, z + k);

                    if(block instanceof TileTotemBase)
                    {
                        int shiftedMusic = getShiftedMusic(instr.getBaseOutput() + bonusMusicAmount, (TileTotemBase) block, instr);

                        playMusic(x, y, z, block, instr, shiftedMusic, instr.getMusicMaximum());
                        return;
                    }
                }
    }

    public static int getShiftedMusic(int defaultMusic, TileTotemBase tileTotemBase, MusicInstrument instr)
    {
        /*int newMusic = defaultMusic;

        //This is a variable that is the shifted amount of musical melody produced, this is shifted because the music has been played too many times

        if(tileTotemBase.musicPlayed[id] > defaultMusic * 1.5)
        {
            newMusic = (newMusic * 3) / 4;
        }

        return newMusic;*/
        //TODO
        return defaultMusic;
    }

    public static void musicParticleAtBlocks(WorldServer world, int xCoord, int yCoord, int zCoord, String particle)
    {
        world.func_147487_a(particle, (double) xCoord + 1, yCoord, zCoord, 2, 0.0D, 0.5D, 0.0D, 0.0D);
        world.func_147487_a(particle, (double) xCoord - 1, yCoord, zCoord, 2, 0.0D, 0.5D, 0.0D, 0.0D);
        world.func_147487_a(particle, xCoord, yCoord, (double) zCoord + 1, 2, 0.0D, 0.5D, 0.0D, 0.0D);
        world.func_147487_a(particle, xCoord, yCoord, (double) zCoord - 1, 2, 0.0D, 0.5D, 0.0D, 0.0D);
    }


}
