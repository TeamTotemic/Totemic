package pokefenn.totemic.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import pokefenn.totemic.init.ModEntityTypes;

public class TotemicEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public TotemicEntityTypeTagsProvider(PackOutput pOutput, CompletableFuture<Provider> pProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(Provider pProvider) {
        tag(EntityTypeTags.ARROWS).add(ModEntityTypes.invisible_arrow.get());
        tag(Tags.EntityTypes.BOSSES).add(ModEntityTypes.baykok.get());
    }

    @Override
    public String getName() {
        return "Totemic EntityType tags";
    }
}
