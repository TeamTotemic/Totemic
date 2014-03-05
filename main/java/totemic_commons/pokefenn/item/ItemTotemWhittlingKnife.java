package totemic_commons.pokefenn.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.block.BlockTotemWoods;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.EntityUtil;

public class ItemTotemWhittlingKnife extends ItemNormal
{

    public ItemTotemWhittlingKnife(int id)
    {
        super(id);
        setMaxStackSize(1);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TOTEM_WHITTLING_KNIFE_NAME);
        setContainerItem(this);
        setMaxDamage(2);

    }

    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (!world.isRemote)
        {
            MovingObjectPosition block = EntityUtil.raytraceFromEntity(world, player, true, 5);

            if (block != null)
            {
                Block blockQuery = Block.blocksList[world.getBlockId(block.blockX, block.blockY, block.blockZ)];

                if (blockQuery != null)
                {
                    if (blockQuery instanceof BlockTotemWoods)
                    {
                        world.setBlock(block.blockX, block.blockY, block.blockZ, ModBlocks.totemSocket.blockID);
                        //player.getHeldItem().setItemDamage(player.getHeldItem().getItemDamage() + 1);
                        player.getHeldItem().damageItem(1, player);
                    }

                }
            }
        }

        return true;
    }


}


