package totemic_commons.pokefenn.item.equipment;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.api.TotemicStaffUsage;
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
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced)
    {
        list.add(I18n.format(getUnlocalizedName() + ".tooltip"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        Block block = world.getBlockState(pos).getBlock();
        if(block instanceof TotemicStaffUsage)
        {
            return ((TotemicStaffUsage) block).onTotemicStaffRightClick(world, pos, player, player.getHeldItem(hand));
        }
        return EnumActionResult.FAIL;
    }
}
