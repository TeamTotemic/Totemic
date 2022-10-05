package pokefenn.totemic.item.music;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModItems;

public class FluteItem extends Item {
    public FluteItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if(player.isShiftKeyDown())
            TotemicAPI.get().music().playSelector(player, ModContent.flute);
        else
            TotemicAPI.get().music().playMusic(world, player.getX(), player.getY(), player.getZ(), player, ModContent.flute, MusicAPI.DEFAULT_RANGE, getMusicAmount(world));

        player.getCooldowns().addCooldown(ModItems.flute.get(), 20);
        player.getCooldowns().addCooldown(ModItems.infused_flute.get(), 20);

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    protected int getMusicAmount(Level level) {
        return ModContent.flute.getBaseOutput();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("totemic.tooltip.selectorMode"));
    }
}
