package pokefenn.totemic.block.totem;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectAPI;

public class TotemPoleBlock extends HorizontalBlock {
    public final TotemWoodType woodType;
    public final TotemEffect effect;

    public TotemPoleBlock(TotemWoodType woodType, TotemEffect effect, Properties properties) {
        super(properties);
        this.woodType = woodType;
        this.effect = effect;
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if(facing == Direction.UP) {
            for(int i = 0; i < TotemEffectAPI.MAX_POLE_SIZE; i++) {
                BlockPos searchPos = currentPos.below(i + 1);
                BlockState searchState = world.getBlockState(searchPos);
                if(searchState.getBlock() instanceof TotemBaseBlock) {
                    searchState.updateShape(Direction.UP, state, world, searchPos, currentPos);
                }
                else if(!(searchState.getBlock() instanceof TotemPoleBlock))
                    break;
            }
        }
        return state;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IFormattableTextComponent getName() {
        return new TranslationTextComponent(this.getDescriptionId(), effect.getDisplayName());
    }

    @Override
    public String getDescriptionId() {
        return "block.totemic." + woodType.getName() + "_totem_pole";
    }
}
