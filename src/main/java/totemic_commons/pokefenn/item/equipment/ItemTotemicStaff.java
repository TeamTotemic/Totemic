package totemic_commons.pokefenn.item.equipment;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ITotemicStaffUsage;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;

public class ItemTotemicStaff extends ItemTotemic
{
    public ItemTotemicStaff()
    {
        super(Strings.TOTEMIC_STAFF_NAME);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("A staff for your Totemic needs!");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.uncommon;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            //EntityBuffalo buffalo = new EntityBuffalo(world);
            //buffalo.setPosition(player.posX, player.posY, player.posZ);
            //world.spawnEntityInWorld(buffalo);
            Block block = world.getBlock(x, y, z);
            if(block instanceof ITotemicStaffUsage)
            {
                ((ITotemicStaffUsage) block).onBasicRightClick(x, y, z, player, world, stack);
            }

        }

        return true;
    }


}
