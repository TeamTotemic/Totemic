package totemic_commons.pokefenn.item.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.block.BlockTotemWoods;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.List;

public class ItemTotemWhittlingKnife extends ItemTotemic
{

    public ItemTotemWhittlingKnife()
    {
        super();
        setMaxStackSize(1);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TOTEM_WHITTLING_KNIFE_NAME);
        setContainerItem(this);
        setMaxDamage(2);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("A knife for all your whittlin' needs");
    }

    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if(!world.isRemote)
        {
            MovingObjectPosition block = EntityUtil.raytraceFromEntity(world, player, true, 5);

            if(block != null)
            {
                Block blockQuery = world.getBlock(block.blockX, block.blockY, block.blockZ);

                if(blockQuery != null)
                {
                    if(blockQuery instanceof BlockTotemWoods)
                    {
                        world.setBlock(block.blockX, block.blockY, block.blockZ, ModBlocks.totemSocket);
                    }

                }
            }
        }

        return true;
    }


}


