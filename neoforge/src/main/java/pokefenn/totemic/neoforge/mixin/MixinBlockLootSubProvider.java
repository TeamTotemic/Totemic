package pokefenn.totemic.neoforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.loot.CanItemPerformAbility;

/*
 * A mixin that is only used for data generation, for replacing the "minecraft:match_tool" condition
 * for Cedar Leaves with "neoforge:can_item_perform_ability" without having to copy a bunch of
 * code from BlockLootSubProvider.
 */
@Mixin(BlockLootSubProvider.class)
public abstract class MixinBlockLootSubProvider {
    @Inject(method = "hasShears", at = @At("RETURN"), cancellable = true, require = 0)
    private void onHasShears(CallbackInfoReturnable<LootItemCondition.Builder> ci) {
        ci.setReturnValue(CanItemPerformAbility.canItemPerformAbility(ItemAbilities.SHEARS_DIG));
    }
}
