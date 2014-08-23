package totemic_commons.pokefenn.item.equipment;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.totem.TotemRegistry;
import totemic_commons.pokefenn.block.totem.BlockTotemBase;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.List;

public class ItemTotemWhittlingKnife extends ItemTotemic
{

    int time = 0;

    public ItemTotemWhittlingKnife()
    {
        super(Strings.TOTEM_WHITTLING_KNIFE_NAME);
        setMaxStackSize(1);
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

                if(blockQuery instanceof BlockLog && ((blockQuery.getClass().toString().contains("net.minecraft")) || blockQuery == ModBlocks.cedarLog))
                {
                    int blockMetadata = world.getBlockMetadata(block.blockX, block.blockY, block.blockZ);

                    if(itemStack.getItemDamage() == TotemRegistry.totemEffect.size())
                    {

                        world.setBlock(block.blockX, block.blockY, block.blockZ, ModBlocks.totemPole, blockMetadata < 4 ? blockMetadata : 5, 2);
                        if(world.getBlock(block.blockX, block.blockY, block.blockZ) instanceof BlockTotemBase)
                        {
                            TileTotemBase tileTotemBase = (TileTotemBase) world.getTileEntity(block.blockX, block.blockY, block.blockZ);
                            //tileTotemBase.bindedPlayer = player.getDisplayName();
                        }
                        return true;
                    } else
                    {
                        if(itemStack.getItemDamage() < TotemRegistry.totemEffect.size())
                        {

                            world.setBlock(block.blockX, block.blockY, block.blockZ, ModBlocks.totemPole, blockMetadata < 4 ? blockMetadata : 5, 2);
                            TileTotemPole tileTotemSocket = (TileTotemPole) world.getTileEntity(block.blockX, block.blockY, block.blockZ);

                            tileTotemSocket.totemId = TotemRegistry.getRecipes().get(itemStack.getItemDamage()).getTotemId();
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


