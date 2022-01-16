package pokefenn.totemic.item.music;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModItems;

public class FluteItem extends Item {
    public FluteItem(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("null")
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        TotemicAPI.get().music().playMusic(player, ModContent.flute);

        player.getCooldowns().addCooldown(ModItems.flute, 20);
        player.getCooldowns().addCooldown(ModItems.infused_flute, 20);

        player.awardStat(Stats.ITEM_USED.get(this));
        return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, player.getItemInHand(hand));
    }
}
