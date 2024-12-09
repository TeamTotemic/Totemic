package pokefenn.totemic.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicEntityTypeTags;
import pokefenn.totemic.init.ModEntityTypes;

public final class TotemicEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public TotemicEntityTypeTagsProvider(PackOutput pOutput, CompletableFuture<Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, TotemicAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider pProvider) {
        //Totemic tags
        tag(TotemicEntityTypeTags.BUFFALO_DANCE_TARGETS).add(EntityType.COW);
        tag(TotemicEntityTypeTags.EAGLE_DANCE_TARGETS).add(EntityType.PARROT);
        tag(TotemicEntityTypeTags.HYMN_OF_MATURITY_BLACKLIST); //don't add anything by default, just create the JSON file

        //Minecraft and Forge tags
        tag(EntityTypeTags.ARROWS).add(ModEntityTypes.invisible_arrow.get());
        tag(Tags.EntityTypes.BOSSES).add(ModEntityTypes.baykok.get());
    }
}
