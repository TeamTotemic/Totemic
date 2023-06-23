package pokefenn.totemic.item.music;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(player.isShiftKeyDown())
            TotemicAPI.get().music().playSelector(player, ModContent.flute);
        else
            TotemicAPI.get().music().playMusic(level, player.position(), player, ModContent.flute, MusicAPI.DEFAULT_RANGE, getMusicAmount(level.random));

        player.getCooldowns().addCooldown(ModItems.flute.get(), 20);
        player.getCooldowns().addCooldown(ModItems.infused_flute.get(), 20);

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }

    protected int getMusicAmount(Random rand) {
        return ModContent.flute.getBaseOutput();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TranslatableComponent("totemic.tooltip.selectorMode"));
    }
}
