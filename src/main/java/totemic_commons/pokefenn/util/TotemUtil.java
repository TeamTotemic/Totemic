package totemic_commons.pokefenn.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.music.IMusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

import java.util.Random;

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
        else if(music > 0 && music < 50)
            unlocalized = "little";
        else if(music > 50 && music < 75)
            unlocalized = "littleMedium";
        else if(music > 75 && music < 100)
            unlocalized = "medium";
        else if(music > 100 && music < 125)
            unlocalized = "mediumLarge";
        else if(music > 125 && music < 150)
            unlocalized = "large";
        else if(music > 150)
            unlocalized = "crazyLarge";

        return welp + unlocalized;
    }

    public static ItemStack getItemStackFromEnum(MusicEnum musicEnum)
    {
        if(musicEnum == MusicEnum.DRUM)
            return new ItemStack(ModBlocks.drum);
        else if(musicEnum == MusicEnum.FLUTE)
            return new ItemStack(ModItems.flute);
        else if(musicEnum == MusicEnum.JINGLE_DRESS)
            return new ItemStack(ModItems.jingleDress);
        else if(musicEnum == MusicEnum.WIND_CHIME)
            return new ItemStack(ModBlocks.windChime);
        else if(musicEnum == MusicEnum.RATTLE)
            return new ItemStack(ModItems.ceremonialRattle);

        return null;
    }

    public static void addPotionEffects(EntityPlayer player, int defaultTime, Potion potion, int defaultStrength, int totemWoodBonus, int repetitionBonus, int melodyAmount)
    {
        Random random = new Random();

        player.addPotionEffect(new PotionEffect(potion.getId(), defaultTime + (repetitionBonus * 10) + (random.nextInt(6) * 8) + melodyAmount + (totemWoodBonus * 10), defaultStrength + (repetitionBonus == 5 || melodyAmount > 112 ? 1 : 0)));
    }

    public static void addNegativePotionEffect(EntityPlayer player, int defaultTime, Potion potion, int defaultStrength, int totemWoodBonus, int repetitionBonus, int melodyAmount)
    {
        player.addPotionEffect(new PotionEffect(potion.id, defaultTime - (totemWoodBonus * 8) - (repetitionBonus * 7) - (melodyAmount / 32), defaultStrength - (melodyAmount > 112 ? 1 : 0)));
    }

    public static void playMusicFromItemForCeremonySelector(ItemStack itemStack, EntityPlayer player, int x, int y, int z, MusicEnum musicEnum, int radius)
    {
        World world = player.worldObj;

        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    if(world.getBlock(x + i, y + j, z + k) != null)
                    {
                        if(world.getTileEntity(x + i, y + j, z + k) instanceof IMusicAcceptor && ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).doesMusicSelect() && ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).isMusicSelecting())
                        {
                            setSelectors(x, y, z, world.getTileEntity(x + i, y + j, z + k), musicEnum, i, j, k);
                            return;
                        }
                    }
                }
    }

    public static void setSelectors(int x, int y, int z, TileEntity tileEntity, MusicEnum musicEnum, int i, int j, int k)
    {
        World world = tileEntity.getWorldObj();

        if(world.getTileEntity(x + i, y + j, z + k) instanceof TileTotemBase)
            ((TileTotemBase) world.getTileEntity(x + i, y + j, z + k)).isCeremony = true;

        int[] musicSelectorArray = ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).getMusicSelector();

        if(musicSelectorArray[0] == 0)
        {
            musicSelectorArray[0] = musicEnum.ordinal() + 1;
            musicParticleAtBlocks(world, x + i, y + j, z + k, "note");
        } else if(musicSelectorArray[1] == 0)
        {
            musicSelectorArray[1] = musicEnum.ordinal() + 1;
            musicParticleAtBlocks(world, x + i, y + j, z + k, "note");
        } else if(musicSelectorArray[2] == 0)
        {
            musicSelectorArray[2] = musicEnum.ordinal() + 1;
            musicParticleAtBlocks(world, x + i, y + j, z + k, "note");
        } else if(musicSelectorArray[3] == 0)
        {
            musicSelectorArray[3] = musicEnum.ordinal() + 1;
            musicParticleAtBlocks(world, x + i, y + j, z + k, "note");
        }
        world.markBlockForUpdate(x, y, z);
    }

    public static void playMusicFromBlockForCeremonySelector(World world, EntityPlayer player, int x, int y, int z, MusicEnum musicEnum, int radius)
    {
        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    if(world.getBlock(x + i, y + j, z + k) != null)
                    {
                        if(world.getTileEntity(x + i, y + j, z + k) instanceof IMusicAcceptor && ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).doesMusicSelect() && ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).isMusicSelecting())
                        {
                            setSelectors(x, y, z, world.getTileEntity(x + i, y + j, z + k), musicEnum, i, j, k);
                        }
                    }
                }
    }

    public static void playMusicForCeremony(TileTotemic tileCeremony, MusicEnum musicEnum, int radius, int musicMaximum, int musicAmount)
    {
        World world = tileCeremony.getWorldObj();

        int x = tileCeremony.xCoord;
        int y = tileCeremony.yCoord;
        int z = tileCeremony.zCoord;

        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    if(world.getBlock(x + i, y + j, z + k) != null)
                    {
                        TileEntity block = world.getTileEntity(x + i, y + j, z + k);

                        if(block instanceof IMusicAcceptor && block instanceof TileTotemBase)
                        {
                            addMusicPlayed((TileTotemBase) block, musicEnum);
                            int shiftedMusic = getShiftedMusic(musicAmount, (TileTotemBase) block, musicEnum);

                            playMusic(x, y, z, block, musicEnum, i, j, k, shiftedMusic, musicMaximum);
                            return;
                        }

                    }
                }
    }

    public static void playMusic(int x, int y, int z, TileEntity tileEntity, MusicEnum musicEnum, int i, int j, int k, int musicAmount, int musicMaximum)
    {
        World world = tileEntity.getWorldObj();

        int[] musicArray = ((IMusicAcceptor) tileEntity).getMusicArray();

        if(((IMusicAcceptor) tileEntity).getIsCeremony())
        {
            if(!((IMusicAcceptor) tileEntity).isDoingEndingEffect())
            {
                if(musicArray[musicEnum.ordinal()] + musicAmount > musicMaximum)
                {
                    musicArray[musicEnum.ordinal()] = musicMaximum;
                    musicParticleAtBlocks(world, x + i, y + j, z + k, "cloud");
                    musicParticleAtBlocks(world, x + i, y + j, z + k, "note");
                    return;

                } else if(musicArray[musicEnum.ordinal()] + musicAmount < musicMaximum)
                {
                    musicArray[musicEnum.ordinal()] += musicAmount;
                    musicParticleAtBlocks(world, x + i, y + j, z + k, "note");
                    return;
                }
            }
        } else
        {
            if(tileEntity instanceof TileTotemBase)
            {
                if(((TileTotemBase) tileEntity).musicForTotemEffect + (musicAmount / 2) > TileTotemBase.maximumMusic)
                {
                    ((TileTotemBase) tileEntity).musicForTotemEffect = TileTotemBase.maximumMusic;
                    musicParticleAtBlocks(world, x + i, y + j, z + k, "cloud");
                    return;
                } else
                {
                    ((TileTotemBase) tileEntity).musicForTotemEffect += (musicAmount / 2);
                    musicParticleAtBlocks(world, x + i, y + j, z + k, "note");
                    return;
                }
            }
        }
        world.markBlockForUpdate(x, y, z);
    }

    public static void playMusicFromItem(World world, EntityPlayer player, MusicEnum musicEnum, int x, int y, int z, int radius, int musicMaximum, int musicAmount)
    {
        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    if(world.getBlock(x + i, y + j, z + k) != null)
                    {
                        TileEntity block = world.getTileEntity(x + i, y + j, z + k);

                        if(block instanceof IMusicAcceptor && block instanceof TileTotemBase)
                        {
                            addMusicPlayed((TileTotemBase) block, musicEnum);
                            int shiftedMusic = getShiftedMusic(musicAmount, (TileTotemBase) block, musicEnum);

                            playMusic(x, y, z, block, musicEnum, i, j, k, shiftedMusic, musicMaximum);
                            return;
                        }
                    }

                }
    }

    public static void addMusicPlayed(TileTotemBase tileTotemBase, MusicEnum musicEnum)
    {
        if(tileTotemBase != null)
        {
            tileTotemBase.musicPlayed[musicEnum.ordinal()]++;
        }
    }

    public static int getShiftedMusic(int defaultMusic, TileTotemBase tileTotemBase, MusicEnum musicEnum)
    {
        int newMusic = defaultMusic;

        //This is a variable that is the shifted amount of musical melody produced, this is shifted because the music has been played too many times

        if(tileTotemBase.musicPlayed[musicEnum.ordinal()] > defaultMusic * 1.5)
        {
            newMusic = (newMusic / 4) * 3;
        }

        return newMusic;
    }

    public static void musicParticleAtBlocks(World world, int xCoord, int yCoord, int zCoord, String particle)
    {
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a(particle, (double) xCoord + 1, (double) yCoord, (double) zCoord, 2, 0.0D, 0.5D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a(particle, (double) xCoord - 1, (double) yCoord, (double) zCoord, 2, 0.0D, 0.5D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a(particle, (double) xCoord, (double) yCoord, (double) zCoord + 1, 2, 0.0D, 0.5D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a(particle, (double) xCoord, (double) yCoord, (double) zCoord - 1, 2, 0.0D, 0.5D, 0.0D, 0.0D);
    }


}
