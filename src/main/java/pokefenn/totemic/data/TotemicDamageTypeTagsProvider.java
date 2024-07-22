package pokefenn.totemic.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModResources;

public final class TotemicDamageTypeTagsProvider extends DamageTypeTagsProvider {
    public TotemicDamageTypeTagsProvider(PackOutput pOutput, CompletableFuture<Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, TotemicAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider pProvider) {
        tag(DamageTypeTags.BYPASSES_ARMOR).add(ModResources.SUN_DANCE_DMG);
        tag(DamageTypeTags.BYPASSES_EFFECTS).add(ModResources.SUN_DANCE_DMG);
        tag(DamageTypeTags.NO_KNOCKBACK).add(ModResources.SUN_DANCE_DMG);
    }
}
