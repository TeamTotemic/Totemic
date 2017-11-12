package pokefenn.totemic.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.init.ModVillagers;
import pokefenn.totemic.lib.Strings;

public class ItemSpawnTotemicVillager extends ItemTotemic
{
    public ItemSpawnTotemicVillager()
    {
        super(Strings.SPAWN_VILLAGER_NAME);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
            EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(world.isRemote)
            return EnumActionResult.SUCCESS;
        pos = pos.offset(facing);
        EntityVillager villager = new EntityVillager(world);
        villager.setProfession(ModVillagers.profTotemist);
        villager.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        world.spawnEntity(villager);
        player.getHeldItem(hand).shrink(1);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        return "Spawn Medicine Man";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add("For BTM Moon only");
    }
}
