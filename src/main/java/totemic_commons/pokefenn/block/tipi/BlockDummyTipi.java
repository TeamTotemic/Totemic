package totemic_commons.pokefenn.block.tipi;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import totemic_commons.pokefenn.lib.Strings;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockDummyTipi extends Block
{
    public BlockDummyTipi()
    {
        super(Material.cloth);
        setBlockName(Strings.DUMMY_TIPI_NAME);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }
}
