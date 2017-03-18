package totemic_commons.pokefenn.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

public class BlockCedarPlank extends Block
{
    public BlockCedarPlank()
    {
        super(Material.WOOD);
        setRegistryName(Strings.RED_CEDAR_PLANK_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.RED_CEDAR_PLANK_NAME);
        setHardness(2F);
        setSoundType(SoundType.WOOD);
        setCreativeTab(Totemic.tabsTotem);
    }
}
