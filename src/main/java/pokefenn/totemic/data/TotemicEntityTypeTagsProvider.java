package pokefenn.totemic.data;

import javax.annotation.Nullable;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import pokefenn.totemic.init.ModEntityTypes;

public class TotemicEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public TotemicEntityTypeTagsProvider(DataGenerator pGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(EntityTypeTags.ARROWS).add(ModEntityTypes.invisible_arrow.get());
        tag(Tags.EntityTypes.BOSSES).add(ModEntityTypes.baykok.get());
    }

    @Override
    public String getName() {
        return "Totemic EntityType tags";
    }
}
