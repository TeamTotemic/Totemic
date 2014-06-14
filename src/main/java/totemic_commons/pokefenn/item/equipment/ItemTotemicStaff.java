package totemic_commons.pokefenn.item.equipment;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ITotemicStaffUsage;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.List;

public class ItemTotemicStaff extends ItemTotemic
{


    public ItemTotemicStaff()
    {
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TOTEMIC_STAFF_NAME);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("A staff for your Totemic needs!");
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
            //EntityBuffalo buffalo = new EntityBuffalo(world);
            //buffalo.setPosition(player.posX, player.posY, player.posZ);
            //world.spawnEntityInWorld(buffalo);

            MovingObjectPosition block = EntityUtil.raytraceFromEntity(world, player, true, 5);

            if(block != null)
            {
                Block blockQuery = (world.getBlock(block.blockX, block.blockY, block.blockZ));

                if(blockQuery != null)
                {
                    if(blockQuery instanceof ITotemicStaffUsage)
                    {
                        ((ITotemicStaffUsage) blockQuery).onBasicRightClick(block.blockX, block.blockY, block.blockZ, player, world, par1ItemStack);
                        return true;
                    }
                }
            }

        }

        return true;
    }


}
