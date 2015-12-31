package totemic_commons.pokefenn.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockCedarPlank extends Block
{
    public BlockCedarPlank()
    {
        super(Material.wood);
        setBlockName(Strings.RED_CEDAR_PLANK_NAME);
        setHardness(2F);
        setBlockTextureName(Resources.TEXTURE_LOCATION + ":" + "cedarPlank");
        setStepSound(soundTypeWood);
        setCreativeTab(Totemic.tabsTotem);
    }

}
