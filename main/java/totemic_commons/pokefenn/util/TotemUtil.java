package totemic_commons.pokefenn.util;

import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.bauble.ITotemBauble;
import totemic_commons.pokefenn.api.music.IMusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.item.equipment.armour.ItemTotemArmour;
import totemic_commons.pokefenn.tileentity.TileTotemic;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 06/02/14
 * Time: 17:07
 */
public class TotemUtil
{


    public static int getArmourAmounts(EntityPlayer player)
    {
        int j = 0;
        for(int i = 0; i < 4; i++)
            if(player.inventory.armorItemInSlot(i) != null)
                if(player.inventory.armorItemInSlot(i).getItem() instanceof ItemTotemArmour)
                    j++;

        return j;
    }

    public static void addPotionEffects(EntityPlayer player, int defaultTime, int multiplicationAmount, Potion potion, int defaultStrength, boolean baubleIncrease)
    {
        int armourAmounts = getArmourAmounts(player);

        player.addPotionEffect(new PotionEffect(potion.id, defaultTime + ((armourAmounts + getTotemBaublesAmount(player)) * multiplicationAmount), baubleIncrease ? getStrength(player, defaultStrength) + getTotemBaublesAmount(player) : getStrength(player, defaultStrength)));
    }

    public static void addNegitivePotionEffect(EntityPlayer player, int defaultTime, int multiplicationAmount, Potion potion, int defaultStrength, boolean baubleIncrease)
    {
        int armourAmounts = getArmourAmounts(player);

        int totalDecrement = armourAmounts + getTotemBaublesAmount(player);

        if(totalDecrement < 4)
            player.addPotionEffect(new PotionEffect(potion.id, defaultTime - ((armourAmounts + getTotemBaublesAmount(player)) * multiplicationAmount), getStrengthForNegitive(player, defaultStrength)));
    }

    public static int getStrength(EntityPlayer player, int defaultStrength)
    {
        return getArmourAmounts(player) > 2 ? defaultStrength + 1 : defaultStrength;
    }

    public static int getStrengthForNegitive(EntityPlayer player, int defaultStrength)
    {
        return getArmourAmounts(player) > 2 ? defaultStrength - 1 : defaultStrength;
    }

    public static int getTotemBaublesAmount(EntityPlayer player)
    {
        int j = 0;

        IInventory baubleInventory = BaublesApi.getBaubles(player);

        for(int i = 0; i < baubleInventory.getSizeInventory(); i++)
        {
            if(baubleInventory.getStackInSlot(i) != null)
            {
                if(baubleInventory.getStackInSlot(i).getItem() instanceof ITotemBauble)
                {
                    j += ((ITotemBauble) baubleInventory.getStackInSlot(i).getItem()).getTotemEfficiency(player.worldObj, baubleInventory.getStackInSlot(i), player);
                }
            }

        }

        return j;
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
                        if(world.getTileEntity(x + i, y + j, z + k) instanceof IMusicAcceptor && ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).doesMusicSelect())
                        {
                            int[] musicSelectorArray = ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).getMusicSelector();

                            if(musicSelectorArray[0] == 0)
                            {
                                musicSelectorArray[0] = musicEnum.ordinal();
                                return;
                            } else if(musicSelectorArray[1] == 0)
                            {
                                musicSelectorArray[1] = musicEnum.ordinal();
                                return;
                            } else if(musicSelectorArray[2] == 0)
                            {
                                musicSelectorArray[2] = musicEnum.ordinal();
                                return;
                            } else if(musicSelectorArray[3] == 0)
                            {
                                musicSelectorArray[3] = musicEnum.ordinal();
                                return;
                            }
                            world.markBlockForUpdate(x, y, z);
                        }
                    }
                }
    }

    public static void playMusicFromBlockForCeremonySelector(World world, EntityPlayer player, int x, int y, int z, MusicEnum musicEnum, int radius)
    {
        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    if(world.getBlock(x + i, y + j, z + k) != null)
                    {
                        if(world.getTileEntity(x + i, y + j, z + k) instanceof IMusicAcceptor && ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).doesMusicSelect())
                        {
                            int[] musicSelectorArray = ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).getMusicSelector();

                            if(musicSelectorArray[0] != 0)
                            {
                                musicSelectorArray[0] = musicEnum.ordinal();
                                return;
                            } else if(musicSelectorArray[1] != 0)
                            {
                                musicSelectorArray[1] = musicEnum.ordinal();
                                return;
                            } else if(musicSelectorArray[2] != 0)
                            {
                                musicSelectorArray[2] = musicEnum.ordinal();
                                return;
                            } else if(musicSelectorArray[3] != 0)
                            {
                                musicSelectorArray[3] = musicEnum.ordinal();
                                return;
                            }
                            world.markBlockForUpdate(x, y, z);
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

                        if(block instanceof IMusicAcceptor)
                        {
                            int[] musicArray = ((IMusicAcceptor) block).getMusicArray();

                            if(musicArray[musicEnum.ordinal()] < musicArray.length + 1)
                            {
                                if(musicArray[musicEnum.ordinal()] + musicAmount > musicMaximum)
                                {
                                    musicArray[musicEnum.ordinal()] = musicMaximum;
                                    return;

                                } else if(musicArray[musicEnum.ordinal()] + musicAmount < musicMaximum)
                                {
                                    musicArray[musicEnum.ordinal()] += musicAmount;
                                    return;
                                }
                                world.markBlockForUpdate(x, y, z);
                            }
                        }

                    }
                }
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

                        if(block instanceof IMusicAcceptor)
                        {
                            int[] musicArray = ((IMusicAcceptor) block).getMusicArray();

                            if(musicArray[musicEnum.ordinal()] < musicArray.length + 1)
                            {
                                if(musicArray[musicEnum.ordinal()] + musicAmount > musicMaximum)
                                {
                                    musicArray[musicEnum.ordinal()] = musicMaximum;
                                    return;

                                } else if(musicArray[musicEnum.ordinal()] + musicAmount < musicMaximum)
                                {
                                    musicArray[musicEnum.ordinal()] += musicAmount;
                                    return;
                                }
                                world.markBlockForUpdate(x, y, z);
                            }
                        }

                    }
                }
    }


}
