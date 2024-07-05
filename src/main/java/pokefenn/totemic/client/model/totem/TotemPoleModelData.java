package pokefenn.totemic.client.model.totem;

import net.minecraftforge.client.model.data.ModelProperty;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.init.ModContent;

public record TotemPoleModelData(TotemWoodType woodType, TotemCarving carving) {
    public static final ModelProperty<TotemPoleModelData> DATA_PROPERTY = new ModelProperty<>();
    public static final ModelProperty<TotemWoodType> WOOD_TYPE_PROPERTY = new ModelProperty<>();

    public static final TotemPoleModelData DEFAULT = new TotemPoleModelData(ModContent.oak.get(), ModContent.none.get());
}
