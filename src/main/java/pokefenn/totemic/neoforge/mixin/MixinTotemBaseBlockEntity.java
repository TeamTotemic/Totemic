package pokefenn.totemic.neoforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.neoforge.client.TotemPoleModelData;

/*
 * A mixin into our own class, so that we can override the getModelData method. This method refers to the Neo-only type
 * ModelData, so there's no way of declaring this method in the common codebase.
 */
@Mixin(TotemBaseBlockEntity.class)
public abstract class MixinTotemBaseBlockEntity extends BlockEntity {
    @Shadow
    private TotemWoodType woodType;

    @Override
    public ModelData getModelData() {
        return ModelData.builder().with(TotemPoleModelData.WOOD_TYPE_PROPERTY, woodType).build();
    }

    private MixinTotemBaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }
}
