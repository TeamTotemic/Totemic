package pokefenn.totemic.neoforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.block.totem.entity.TotemPoleBlockEntity;
import pokefenn.totemic.neoforge.client.TotemPoleModelData;

// Self-mixin, see MixinTotemBaseBlockEntity
@Mixin(TotemPoleBlockEntity.class)
public abstract class MixinTotemPoleBlockEntity extends BlockEntity {
    @Shadow
    private TotemWoodType woodType;
    @Shadow
    private TotemCarving carving;

    @Override
    public ModelData getModelData() {
        return ModelData.builder().with(TotemPoleModelData.DATA_PROPERTY, new TotemPoleModelData(woodType, carving)).build();
    }

    private MixinTotemPoleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }
}
