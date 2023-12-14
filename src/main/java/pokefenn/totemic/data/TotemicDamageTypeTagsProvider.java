package pokefenn.totemic.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModResources;

public final class TotemicDamageTypeTagsProvider extends DamageTypeTagsProvider {
    public TotemicDamageTypeTagsProvider(PackOutput pOutput, CompletableFuture<Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, TotemicAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider pProvider) {
        //FIXME: Have to use addOptional, as the add method causes an error about missing references for some reason
        tag(DamageTypeTags.BYPASSES_ARMOR).addOptional(ModResources.SUN_DANCE_DMG.location());
        tag(DamageTypeTags.BYPASSES_EFFECTS).addOptional(ModResources.SUN_DANCE_DMG.location());
    }
}
