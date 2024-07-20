package pokefenn.totemic.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.serialization.Dynamic;

import net.minecraft.util.datafix.fixes.ItemStackComponentizationFix;
import pokefenn.totemic.handler.ModDataFixes;

@Mixin(ItemStackComponentizationFix.class)
public class MixinItemStackComponentizationFix {
    @Inject(method = "fixItemStack", at = @At("TAIL"))
    private static void totemic$onFixItemStack(ItemStackComponentizationFix.ItemStackData itemStackData, Dynamic<?> tag, CallbackInfo ci) {
        ModDataFixes.fixItemStackComponents(itemStackData, tag);
    }
}
