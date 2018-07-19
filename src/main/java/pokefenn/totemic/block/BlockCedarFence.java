package pokefenn.totemic.block;

import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.lib.Strings;

public class BlockCedarFence extends BlockFence
{
    public BlockCedarFence()
    {
        super(Material.WOOD, Material.WOOD.getMaterialMapColor());
        setRegistryName(Strings.CEDAR_FENCE_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.CEDAR_FENCE_NAME);
        setHardness(2F);
        setResistance(5F);
        setSoundType(SoundType.WOOD);
        setCreativeTab(Totemic.tabsTotem);
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}
