package totemic_commons.pokefenn.item.equipment;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSapling;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.ITotemicStaffUsage;
import totemic_commons.pokefenn.block.plant.BlockTotemSapling;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.List;
import java.util.Random;

public class ItemTotemicStaff extends ItemTotemic
{


    public ItemTotemicStaff()
    {
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TOTEMIC_STAFF_NAME);
        setMaxStackSize(1);
        registerIcons = false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("A staff for your Totemic needs!");
        list.add("This staff is unstable");
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.uncommon;
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if(!world.isRemote)
        {
            MovingObjectPosition block = EntityUtil.raytraceFromEntity(world, player, true, 5);

            if(block != null)
            {
                Block blockQuery = (world.getBlock(block.blockX, block.blockY, block.blockZ));

                if(blockQuery != null)
                {
                    if(blockQuery instanceof ITotemicStaffUsage)
                    {
                        ((ITotemicStaffUsage) blockQuery).onBasicRightClick(block.blockX, block.blockY, block.blockZ, player, world);
                        return true;
                    }

                    if(blockQuery instanceof BlockSapling && !(blockQuery instanceof BlockTotemSapling))
                    {

                        if(world.getBlock(block.blockX + 1, block.blockY - 1, block.blockZ + 1) == ModBlocks.chlorophyll && world.getBlock(block.blockX - 1, block.blockY - 1, block.blockZ - 1) == ModBlocks.chlorophyll && world.getBlock(block.blockX + 1, block.blockY - 1, block.blockZ - 1) == ModBlocks.chlorophyll && world.getBlock(block.blockX - 1, block.blockY - 1, block.blockZ + 1) == ModBlocks.chlorophyll)
                        {
                            Block blockQuery1 = world.getBlock(block.blockX + 1, block.blockY, block.blockZ);
                            Block blockQuery2 = world.getBlock(block.blockX - 1, block.blockY, block.blockZ);
                            Block blockQuery3 = world.getBlock(block.blockX, block.blockY, block.blockZ - 1);
                            Block blockQuery4 = world.getBlock(block.blockX, block.blockY, block.blockZ + 1);

                            if(blockQuery1 != null && blockQuery2 != null && blockQuery3 != null && blockQuery4 != null && blockQuery1 instanceof BlockFlower && blockQuery2 instanceof BlockFlower && blockQuery3 instanceof BlockFlower && blockQuery4 instanceof BlockFlower)
                            {
                                Random rand = new Random();
                                if(rand.nextBoolean())
                                {
                                    world.setBlock(block.blockX, block.blockY, block.blockZ, ModBlocks.totemSapling);

                                    world.setBlockToAir(block.blockX + 1, block.blockY - 1, block.blockZ + 1);
                                    world.setBlockToAir(block.blockX - 1, block.blockY - 1, block.blockZ - 1);
                                    world.setBlockToAir(block.blockX + 1, block.blockY - 1, block.blockZ - 1);
                                    world.setBlockToAir(block.blockX - 1, block.blockY - 1, block.blockZ + 1);

                                    player.attackEntityFrom(DamageSource.generic, 6 + rand.nextInt(4));


                                } else
                                {
                                    player.addChatMessage(new ChatComponentText("The Infused Sapling Creation Failed!"));

                                    world.setBlockToAir(block.blockX + 1, block.blockY - 1, block.blockZ + 1);
                                    world.setBlockToAir(block.blockX - 1, block.blockY - 1, block.blockZ - 1);
                                    world.setBlockToAir(block.blockX + 1, block.blockY - 1, block.blockZ - 1);
                                    world.setBlockToAir(block.blockX - 1, block.blockY - 1, block.blockZ + 1);

                                    player.attackEntityFrom(DamageSource.generic, 6 + rand.nextInt(4));

                                }
                            }

                        }
                    }


                }
            }

        }

        return true;
    }


}
