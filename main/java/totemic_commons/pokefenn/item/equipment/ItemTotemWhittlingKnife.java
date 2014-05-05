package totemic_commons.pokefenn.item.equipment;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.block.BlockTotemWoods;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.item.ItemTotems;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTotem;
import totemic_commons.pokefenn.tileentity.totem.TileTotemSocket;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.List;

public class ItemTotemWhittlingKnife extends ItemTotemic
{

    public ItemTotemWhittlingKnife()
    {
        setMaxStackSize(1);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TOTEM_WHITTLING_KNIFE_NAME);
        setContainerItem(this);
        //setMaxDamage(2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("A knife for all your whittlin' needs");
        list.add("Currently Carving: " + ItemTotems.TOTEM_NAMES[stack.getItemDamage()]);
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            if(player.isSneaking())
            {
                System.out.println("foobar");
                if(itemStack.getItemDamage() <= ItemTotems.TOTEM_NAMES.length)
                {
                    itemStack.setItemDamage(+itemStack.getItemDamage());
                } else
                {
                    itemStack.setItemDamage(0);
                }
            }
        }

        return itemStack;
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if(!world.isRemote)
        {
            System.out.println("yes?");
            MovingObjectPosition block = EntityUtil.raytraceFromEntity(world, player, true, 5);

            if(block != null)
            {
                Block blockQuery = world.getBlock(block.blockX, block.blockY, block.blockZ);

                /**
                 * TODO
                 * Make this instead right click on a wood block, and then place a block with the Totem in it
                 */

                if(blockQuery == ModBlocks.totemSocket)
                {
                    TileTotemSocket tileTotemSocket = (TileTotemSocket) world.getTileEntity(block.blockX, block.blockY, block.blockZ);
                    System.out.println("yes");
                    if(par1ItemStack.getItemDamage() != 0)
                        if(tileTotemSocket.getStackInSlot(0) != null)
                        {
                            if(tileTotemSocket.getStackInSlot(0).getItem() == ModItems.subItems && tileTotemSocket.getStackInSlot(0).getItemDamage() == 4)
                            {
                                tileTotemSocket.setInventorySlotContents(0, new ItemStack(ModItems.totems, par1ItemStack.getItemDamage()));
                                world.markBlockForUpdate(block.blockX, block.blockY, block.blockZ);
                                tileTotemSocket.markDirty();
                            }
                        }
                }
            }
        }

        return true;
    }


}


