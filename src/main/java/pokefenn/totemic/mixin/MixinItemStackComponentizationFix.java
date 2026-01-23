package pokefenn.totemic.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.serialization.Dynamic;

import net.minecraft.util.datafix.fixes.ItemStackComponentizationFix;
import pokefenn.totemic.ModDataFixes;

@Mixin(ItemStackComponentizationFix.class)
public abstract class MixinItemStackComponentizationFix {
    @Inject(method = "fixItemStack", at = @At("RETURN"), require = 0) // Not strictly required, just for migration from earlier MC versions
    private static void totemic$onFixItemStack(ItemStackComponentizationFix.ItemStackData itemStackData, Dynamic<?> tag, CallbackInfo ci) {
        ModDataFixes.fixItemStackComponents(itemStackData, tag);
    }
}
