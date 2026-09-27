package pokefenn.totemic.item.music;

import java.util.function.Consumer;

import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModContent;

public class EagleBoneWhistleItem extends Item {
    public EagleBoneWhistleItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if(player.isShiftKeyDown())
            TotemicAPI.get().music().playSelector(player, ModContent.eagle_bone_whistle.get());
        else
            TotemicAPI.get().music().playMusic(player, ModContent.eagle_bone_whistle.get());

        var stack = player.getItemInHand(hand);
        player.getCooldowns().addCooldown(stack, 20);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        builder.accept(Component.translatable("totemic.tooltip.selectorMode"));
    }
}
