package pokefenn.totemic.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.block.TipiBlock;

@Mixin(BedBlock.class)
public abstract class MixinBedBlock extends HorizontalDirectionalBlock {
    @Inject(method = "findStandUpPosition", at = @At("HEAD"), cancellable = true)
    private static void totemic$onFindStandUpPosition(EntityType<?> entityType, CollisionGetter level, BlockPos pos, Direction direction, float yRot, CallbackInfoReturnable<Optional<Vec3>> ci) {
        if(level.getBlockState(pos).getBlock() instanceof TipiBlock tipi) {
            ci.setReturnValue(Optional.of(tipi.getStandUpPosition(pos, direction)));
        }
    }

    private MixinBedBlock(Properties properties) {
        super(properties);
    }
}
