package pokefenn.totemic.item.music;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
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

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if(!world.isClientSide) {
            if(player.isShiftKeyDown())
                TotemicAPI.get().music().playSelector(player, ModContent.flute);
            else
                TotemicAPI.get().music().playMusic(player, ModContent.flute);
        }

        player.getCooldowns().addCooldown(ModItems.flute.get(), 20);
        player.getCooldowns().addCooldown(ModItems.infused_flute.get(), 20);

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
