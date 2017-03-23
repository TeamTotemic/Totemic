package vazkii.botania.totemic_custom.api.lexicon;

import net.minecraft.util.text.Style;

/**
 * NB: Currently unused
 */
public class KnowledgeType
{
    public final String id;
    public final Style style;
    public final boolean autoUnlock;

    public KnowledgeType(String id, Style color, boolean autoUnlock)
    {
        this.id = id;
        this.style = color;
        this.autoUnlock = autoUnlock;
    }

    public String getUnlocalizedName()
    {
        return "totemic.knowledge." + id;
    }
}
