package totemic_commons.pokefenn.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.totem.StateStartup;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

public class ItemCeremonyCheat extends ItemTotemic
{
    public ItemCeremonyCheat()
    {
        super(Strings.CEREMONY_CHEAT_NAME);
        setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileTotemBase)
        {
            if(world.isRemote)
                return EnumActionResult.SUCCESS;

            TileTotemBase totem = (TileTotemBase) tile;
            if(totem.getState() instanceof StateStartup)
            {
                ((StateStartup) totem.getState()).startCeremony();
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        tooltip.add(I18n.format(getUnlocalizedName() + ".tooltip1"));
        tooltip.add(I18n.format(getUnlocalizedName() + ".tooltip2"));
    }
}
