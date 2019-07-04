package pokefenn.totemic.item.music;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import pokefenn.totemic.init.ModItems;

public class ItemFlute extends Item {
    public ItemFlute(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        // TODO Auto-generated method stub
        player.getCooldownTracker().setCooldown(ModItems.flute, 20);
        player.getCooldownTracker().setCooldown(ModItems.infused_flute, 20);

        player.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, player.getHeldItem(hand));
    }
}
