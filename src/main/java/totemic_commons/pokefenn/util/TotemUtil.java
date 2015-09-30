package totemic_commons.pokefenn.util;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import totemic_commons.pokefenn.api.music.IMusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicHandler;
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

    public static void playMusicFromItemForCeremonySelector(EntityPlayer player, int x, int y, int z, MusicHandler musicHandler, int bonusRadius)
    {
        World world = player.worldObj;
        int radius = musicHandler.getBaseRange() + bonusRadius;

        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    if(world.getBlock(x + i, y + j, z + k) != null)
                    {
                        if(world.getTileEntity(x + i, y + j, z + k) instanceof IMusicAcceptor && ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).doesMusicSelect() && ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).isMusicSelecting())
                        {
                            setSelectors(x, y, z, world.getTileEntity(x + i, y + j, z + k), musicHandler.getMusicId(), i, j, k);
                            return;
                        }
                    }
                }
    }

    public static void setSelectors(int x, int y, int z, TileEntity tileEntity, int id, int i, int j, int k)
    {
        WorldServer world = (WorldServer)tileEntity.getWorldObj();

        if(world.getTileEntity(x + i, y + j, z + k) instanceof TileTotemBase)
            ((TileTotemBase) world.getTileEntity(x + i, y + j, z + k)).isCeremony = true;

        int[] musicSelectorArray = ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).getMusicSelector();

        if(musicSelectorArray[0] == 0)
        {
            musicSelectorArray[0] = id + 1;
            musicParticleAtBlocks(world, x + i, y + j, z + k, "note");
        } else if(musicSelectorArray[1] == 0)
        {
            musicSelectorArray[1] = id + 1;
            musicParticleAtBlocks(world, x + i, y + j, z + k, "note");
        } else if(musicSelectorArray[2] == 0)
        {
            musicSelectorArray[2] = id + 1;
            musicParticleAtBlocks(world, x + i, y + j, z + k, "note");
        } else if(musicSelectorArray[3] == 0)
        {
            musicSelectorArray[3] = id + 1;
            musicParticleAtBlocks(world, x + i, y + j, z + k, "note");
        }
        world.markBlockForUpdate(x, y, z);
    }

    public static void playMusicFromBlockForCeremonySelector(World world, int x, int y, int z, MusicHandler musicHandler, int bonusRadius)
    {
        int radius = musicHandler.getBaseRange() + bonusRadius;
        int id =  musicHandler.getMusicId();
        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    if(world.getBlock(x + i, y + j, z + k) != null)
                    {
                        if(world.getTileEntity(x + i, y + j, z + k) instanceof IMusicAcceptor && ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).doesMusicSelect() && ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).isMusicSelecting())
                        {
                            setSelectors(x, y, z, world.getTileEntity(x + i, y + j, z + k), id, i, j, k);
                        }
                    }
                }
    }

    public static void playMusicForCeremony(TileTotemic tileCeremony, MusicHandler musicHandler, int bonusRadius, int bonusMusicAmount)
    {
        World world = tileCeremony.getWorldObj();

        int x = tileCeremony.xCoord;
        int y = tileCeremony.yCoord;
        int z = tileCeremony.zCoord;

        int radius = bonusRadius + musicHandler.getBaseRange();
        int id = musicHandler.getMusicId();
        int musicAmount = musicHandler.getBaseOutput() + bonusMusicAmount;

        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    if(world.getBlock(x + i, y + j, z + k) != null)
                    {
                        TileEntity block = world.getTileEntity(x + i, y + j, z + k);

                        if(block instanceof IMusicAcceptor && block instanceof TileTotemBase)
                        {
                            addMusicPlayed((TileTotemBase) block, id);
                            int shiftedMusic = getShiftedMusic(musicAmount, (TileTotemBase) block, id);

                            playMusic(x, y, z, block, id, i, j, k, shiftedMusic, musicHandler.getMusicMaximum());
                            return;
                        }

                    }
                }
    }

    public static void playMusic(int x, int y, int z, TileEntity tileEntity, int id, int i, int j, int k, int musicAmount, int musicMaximum)
    {
        WorldServer world = (WorldServer)tileEntity.getWorldObj();
        IMusicAcceptor acc = (IMusicAcceptor) tileEntity;

        int[] musicArray = acc.getMusicArray();

        if(acc.getIsCeremony())
        {
            if(!acc.isDoingEndingEffect())
            {
                if(musicArray[id] + musicAmount > musicMaximum)
                {
                    musicArray[id] = musicMaximum;
                    musicParticleAtBlocks(world, x + i, y + j, z + k, "cloud");
                    musicParticleAtBlocks(world, x + i, y + j, z + k, "note");

                } else if(musicArray[id] + musicAmount < musicMaximum)
                {
                    musicArray[id] += musicAmount;
                    musicParticleAtBlocks(world, x + i, y + j, z + k, "note");
                }
            }
        } else
        {
            if(tileEntity instanceof TileTotemBase)
            {
                TileTotemBase tile = (TileTotemBase) tileEntity;
                if(tile.musicForTotemEffect + (musicAmount / 2) > TileTotemBase.maximumMusic)
                {
                    tile.musicForTotemEffect = TileTotemBase.maximumMusic;
                    musicParticleAtBlocks(world, x + i, y + j, z + k, "cloud");
                } else
                {
                    tile.musicForTotemEffect += (musicAmount / 2);
                    musicParticleAtBlocks(world, x + i, y + j, z + k, "note");
                }
            }
        }
        //world.markBlockForUpdate(x, y, z);
    }

    public static void playMusicFromItem(World world, int x, int y, int z, MusicHandler musicHandler, int bonusRadius, int bonusMusicAmount)
    {
        int radius = musicHandler.getBaseRange() + bonusRadius;
        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    if(world.getBlock(x + i, y + j, z + k) != null)
                    {
                        TileEntity block = world.getTileEntity(x + i, y + j, z + k);

                        if(block instanceof IMusicAcceptor && block instanceof TileTotemBase)
                        {
                            addMusicPlayed((TileTotemBase) block, musicHandler.getMusicId());
                            int shiftedMusic = getShiftedMusic(musicHandler.getBaseOutput() + bonusMusicAmount, (TileTotemBase) block, musicHandler.getMusicId());

                            playMusic(x, y, z, block, musicHandler.getMusicId(), i, j, k, shiftedMusic, musicHandler.getMusicMaximum());
                            return;
                        }
                    }

                }
    }

    public static void addMusicPlayed(TileTotemBase tileTotemBase, int id)
    {
        if(tileTotemBase != null)
        {
            tileTotemBase.musicPlayed[id]++;
        }
    }

    public static int getShiftedMusic(int defaultMusic, TileTotemBase tileTotemBase, int id)
    {
        int newMusic = defaultMusic;

        //This is a variable that is the shifted amount of musical melody produced, this is shifted because the music has been played too many times

        if(tileTotemBase.musicPlayed[id] > defaultMusic * 1.5)
        {
            newMusic = (newMusic / 4) * 3;
        }

        return newMusic;
    }

    public static void musicParticleAtBlocks(WorldServer world, int xCoord, int yCoord, int zCoord, String particle)
    {
        world.func_147487_a(particle, (double) xCoord + 1, yCoord, zCoord, 2, 0.0D, 0.5D, 0.0D, 0.0D);
        world.func_147487_a(particle, (double) xCoord - 1, yCoord, zCoord, 2, 0.0D, 0.5D, 0.0D, 0.0D);
        world.func_147487_a(particle, xCoord, yCoord, (double) zCoord + 1, 2, 0.0D, 0.5D, 0.0D, 0.0D);
        world.func_147487_a(particle, xCoord, yCoord, (double) zCoord - 1, 2, 0.0D, 0.5D, 0.0D, 0.0D);
    }


}
