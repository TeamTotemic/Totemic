package pokefenn.totemic.client.model.totem;

import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.init.ModContent;

public record TotemPoleModelData(TotemWoodType woodType, TotemCarving carving) {
    public static final TotemPoleModelData DEFAULT = new TotemPoleModelData(ModContent.oak, ModContent.none);
}
