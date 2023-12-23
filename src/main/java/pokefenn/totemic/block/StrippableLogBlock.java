package pokefenn.totemic.block;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;

public class StrippableLogBlock extends RotatedPillarBlock {
    private final Supplier<? extends RotatedPillarBlock> strippedBlock;

    public StrippableLogBlock(Supplier<? extends RotatedPillarBlock> strippedBlock, Properties pProperties) {
        super(pProperties);
        this.strippedBlock = strippedBlock;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if(toolAction == ToolActions.AXE_STRIP)
            return strippedBlock.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
        else
            return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
