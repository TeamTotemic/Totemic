package pokefenn.totemic.block;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.init.Blocks;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.lib.Strings;

public class BlockCedarFenceGate extends BlockFenceGate
{
    public BlockCedarFenceGate()
    {
        super(BlockPlanks.EnumType.OAK);
        setRegistryName(Strings.CEDAR_FENCE_GATE_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.CEDAR_FENCE_GATE_NAME);
        setHardness(2F);
        setResistance(5F);
        setSoundType(SoundType.WOOD);
        setCreativeTab(Totemic.tabsTotem);
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}
