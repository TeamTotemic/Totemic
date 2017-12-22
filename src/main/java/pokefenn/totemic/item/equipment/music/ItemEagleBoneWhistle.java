package pokefenn.totemic.item.equipment.music;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.music.ItemInstrument;
import pokefenn.totemic.lib.Strings;

public class ItemEagleBoneWhistle extends ItemInstrument
{
    public ItemEagleBoneWhistle()
    {
        setRegistryName(Strings.EAGLE_BONE_WHISTLE_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.EAGLE_BONE_WHISTLE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if(!world.isRemote)
            useInstrument(stack, player, 20, 0, 0);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.UNCOMMON;
    }
}
