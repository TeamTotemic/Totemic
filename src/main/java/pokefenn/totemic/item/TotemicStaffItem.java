package pokefenn.totemic.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import pokefenn.totemic.api.TotemicStaffUsable;
import pokefenn.totemic.block.totem.TotemBaseBlock;

@SuppressWarnings("deprecation")
public class TotemicStaffItem extends Item {
    public TotemicStaffItem(Properties props) {
        super(props);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(getDescriptionId() + ".tooltip"));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Block block = context.getLevel().getBlockState(context.getClickedPos()).getBlock();
        if(block instanceof TotemicStaffUsable tu)
            return tu.onTotemicStaffRightClick(context);
        else
            return InteractionResult.FAIL;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !(state.getBlock() instanceof TotemBaseBlock) && super.canAttackBlock(state, level, pos, player);
    }
}
