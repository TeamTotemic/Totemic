package totemic_commons.pokefenn.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.armour.ITotemArmour;
import totemic_commons.pokefenn.api.music.IMusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.item.ItemTotemicItems;
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
            return new ItemStack(ModItems.subItems, 1, ItemTotemicItems.flute);
        else if(musicEnum == MusicEnum.JINGLE_DRESS)
            return new ItemStack(ModItems.jingleDress);
        else if(musicEnum == MusicEnum.RATTLE)
            return null; //TODO
        else if(musicEnum == MusicEnum.WIND_CHIME)
            return new ItemStack(ModBlocks.windChime);


        return null;
    }

    public static int getArmourAmounts(EntityPlayer player)
    {
        int j = 0;
        for(int i = 0; i < 4; i++)
            if(player.inventory.armorItemInSlot(i) != null)
                if(player.inventory.armorItemInSlot(i).getItem() instanceof ITotemArmour)
                    j += ((ITotemArmour) player.inventory.armorItemInSlot(i).getItem()).getEfficiency(player.inventory.armorItemInSlot(i));

        return j;
    }

    public static void addPotionEffects(EntityPlayer player, int defaultTime, int multiplicationAmount, Potion potion, int defaultStrength, boolean baubleIncrease)
    {
        int armourAmounts = getArmourAmounts(player);
        //TODO
        //player.addPotionEffect(new PotionEffect(potion.id, defaultTime + ((armourAmounts + getTotemBaublesAmount(player)) * multiplicationAmount), baubleIncrease ? getStrength(player, defaultStrength) + getTotemBaublesAmount(player) : getStrength(player, defaultStrength)));
    }

    public static void addNegitivePotionEffect(EntityPlayer player, int defaultTime, int multiplicationAmount, Potion potion, int defaultStrength, boolean baubleIncrease)
    {
        int armourAmounts = getArmourAmounts(player);

        //int totalDecrement = armourAmounts + getTotemBaublesAmount(player);
        //TODO
        //if(totalDecrement < 4)
        //    player.addPotionEffect(new PotionEffect(potion.id, defaultTime - ((armourAmounts + getTotemBaublesAmount(player)) * multiplicationAmount), getStrengthForNegative(player, defaultStrength)));
    }

    public static int getStrength(EntityPlayer player, int defaultStrength)
    {
        return getArmourAmounts(player) > 2 ? defaultStrength + 1 : defaultStrength;
    }

    public static int getStrengthForNegative(EntityPlayer player, int defaultStrength)
    {
        return getArmourAmounts(player) > 2 ? defaultStrength - 1 : defaultStrength;
    }

    /*
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
    */

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
                            if(world.getTileEntity(x + i, y + j, z + k) instanceof TileTotemBase)
                                ((TileTotemBase) world.getTileEntity(x + i, y + j, z + k)).isCeremony = true;

                            int[] musicSelectorArray = ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).getMusicSelector();

                            if(musicSelectorArray[0] == 0)
                            {
                                musicSelectorArray[0] = musicEnum.ordinal() + 1;
                                return;
                            } else if(musicSelectorArray[1] == 0)
                            {
                                musicSelectorArray[1] = musicEnum.ordinal() + 1;
                                return;
                            } else if(musicSelectorArray[2] == 0)
                            {
                                musicSelectorArray[2] = musicEnum.ordinal() + 1;
                                return;
                            } else if(musicSelectorArray[3] == 0)
                            {
                                musicSelectorArray[3] = musicEnum.ordinal() + 1;
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
                        if(world.getTileEntity(x + i, y + j, z + k) instanceof IMusicAcceptor && ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).doesMusicSelect() && ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).isMusicSelecting())
                        {
                            if(world.getTileEntity(x + i, y + j, z + k) instanceof TileTotemBase)
                                ((TileTotemBase) world.getTileEntity(x + i, y + j, z + k)).isCeremony = true;

                            //System.out.println("get here? :<");

                            int[] musicSelectorArray = ((IMusicAcceptor) world.getTileEntity(x + i, y + j, z + k)).getMusicSelector();

                            if(musicSelectorArray[0] == 0)
                            {
                                musicSelectorArray[0] = musicEnum.ordinal() + 1;
                                return;
                            } else if(musicSelectorArray[1] == 0)
                            {
                                musicSelectorArray[1] = musicEnum.ordinal() + 1;
                                return;
                            } else if(musicSelectorArray[2] == 0)
                            {
                                musicSelectorArray[2] = musicEnum.ordinal() + 1;
                                return;
                            } else if(musicSelectorArray[3] == 0)
                            {
                                musicSelectorArray[3] = musicEnum.ordinal() + 1;
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
                            IMusicAcceptor musicAcceptor = (IMusicAcceptor) block;
                            int[] musicArray = musicAcceptor.getMusicArray();
                            if(!musicAcceptor.getEffectMusic())
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
                            } else
                            {
                                if(block instanceof TileTotemBase)
                                {
                                    if(((TileTotemBase) block).musicForEffect + musicAmount > TileTotemBase.maximumMusic)
                                        ((TileTotemBase) block).musicForEffect = TileTotemBase.maximumMusic;
                                    else
                                        ((TileTotemBase) block).musicForEffect += musicAmount;
                                }
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

                            if(!((IMusicAcceptor) block).getEffectMusic())
                            {
                                if(musicArray[musicEnum.ordinal()] + musicAmount > musicMaximum)
                                {
                                    musicArray[musicEnum.ordinal()] = musicMaximum;
                                    return;

                                } else if(musicArray[musicEnum.ordinal()] + musicAmount < musicMaximum)
                                {
                                    musicArray[musicEnum.ordinal()] += musicAmount;
                                    return;
                                } else
                                {
                                    if(block instanceof TileTotemBase)
                                    {
                                        if(((TileTotemBase) block).musicForEffect + musicAmount > TileTotemBase.maximumMusic)
                                            ((TileTotemBase) block).musicForEffect = TileTotemBase.maximumMusic;
                                        else
                                            ((TileTotemBase) block).musicForEffect += musicAmount;
                                    }
                                }
                            }
                            world.markBlockForUpdate(x, y, z);
                        }
                    }

                }
    }


}
