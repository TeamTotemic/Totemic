package pokefenn.totemic.item.equipment;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
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
import pokefenn.totemic.api.TotemicStaffUsage;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.item.ItemTotemic;
import pokefenn.totemic.lib.Strings;

public class ItemTotemicStaff extends ItemTotemic
{
    public ItemTotemicStaff()
    {
        super(Strings.TOTEMIC_STAFF_NAME);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(I18n.format(getUnlocalizedName() + ".tooltip"));
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
            return ((TotemicStaffUsage) block).onTotemicStaffRightClick(world, pos, player, hand, facing, hitX, hitY, hitZ);
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player)
    {
        return world.getBlockState(pos).getBlock() != ModBlocks.totem_base && super.canDestroyBlockInCreative(world, pos, stack, player);
    }
}
