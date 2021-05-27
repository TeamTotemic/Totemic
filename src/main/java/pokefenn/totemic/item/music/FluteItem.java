package pokefenn.totemic.item.music;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModItems;

public class FluteItem extends Item {
    public FluteItem(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("null")
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        TotemicAPI.get().music().playMusic(player, ModContent.flute);

        player.getCooldowns().addCooldown(ModItems.flute, 20);
        player.getCooldowns().addCooldown(ModItems.infused_flute, 20);

        player.awardStat(Stats.ITEM_USED.get(this));
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, player.getItemInHand(hand));
    }
}
