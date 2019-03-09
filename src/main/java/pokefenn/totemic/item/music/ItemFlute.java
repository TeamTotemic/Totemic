package pokefenn.totemic.item.music;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import pokefenn.totemic.init.ModItems;

public class ItemFlute extends Item {
    public ItemFlute(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        // TODO Auto-generated method stub
        player.getCooldownTracker().setCooldown(ModItems.flute, 20);
        player.getCooldownTracker().setCooldown(ModItems.infused_flute, 20);

        player.addStat(StatList.ITEM_USED.get(this));
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}
