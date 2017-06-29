package pokefenn.totemic.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.lib.Strings;

public class BlockCedarPlank extends Block
{
    public BlockCedarPlank()
    {
        super(Material.WOOD);
        setRegistryName(Strings.CEDAR_PLANK_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.CEDAR_PLANK_NAME);
        setHardness(2F);
        setSoundType(SoundType.WOOD);
        setCreativeTab(Totemic.tabsTotem);
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}
