package totemic_commons.pokefenn.item.equipment;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.recipe.TotemRegistry;
import totemic_commons.pokefenn.block.BlockCedarLog;
import totemic_commons.pokefenn.block.totem.BlockTotemPole;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.List;

public class ItemTotemWhittlingKnife extends ItemTotemic
{

    int time = 0;

    public ItemTotemWhittlingKnife()
    {
        setMaxStackSize(1);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TOTEM_WHITTLING_KNIFE_NAME);
        setContainerItem(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("A knife for all your whittlin' needs");
        list.add("Shift and right click to change carving");
        if(stack.getItemDamage() < TotemRegistry.totemEffect.size())
            list.add("Currently Carving: " + TotemRegistry.totemEffect.get(stack.getItemDamage()).getName());
        if(stack.getItemDamage() == TotemRegistry.totemEffect.size())
            list.add("Currently Carving: Totem Base");
        
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(player.isSneaking() && itemStack.getItemDamage() >= TotemRegistry.totemEffect.size())
            return new ItemStack(this, 1, 0);

        return player.isSneaking() ? new ItemStack(this, 1, 1 + itemStack.getItemDamage()) : itemStack;
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        //TODO make sure that this works
        if(!world.isRemote)
        {
            MovingObjectPosition block = EntityUtil.raytraceFromEntity(world, player, true, 5);

            if(block != null && !player.isSneaking())
            {
                Block blockQuery = world.getBlock(block.blockX, block.blockY, block.blockZ);

                if(blockQuery instanceof BlockCedarLog || blockQuery instanceof BlockTotemPole)
                {
                    if(itemStack.getItemDamage() == TotemRegistry.totemEffect.size())
                    {
                        world.setBlock(block.blockX, block.blockY, block.blockZ, ModBlocks.totemBase);
                        return true;
                    } else
                    {
                        if(itemStack.getItemDamage() < TotemRegistry.totemEffect.size())
                        {
                            world.setBlock(block.blockX, block.blockY, block.blockZ, ModBlocks.totemPole);
                            TileTotemPole tileTotemSocket = (TileTotemPole) world.getTileEntity(block.blockX, block.blockY, block.blockZ);

                            tileTotemSocket.setInventorySlotContents(0, TotemRegistry.getRecipes().get(itemStack.getItemDamage()).getTotem());
                            world.markBlockForUpdate(block.blockX, block.blockY, block.blockZ);
                            tileTotemSocket.markDirty();
                            return true;
                        }
                    }
                }

            }
        }

        return true;
    }


}


