package totemic_commons.pokefenn.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileSpiritualEnergyCollector;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockSpiritualEnergyCollector extends BlockTileTotemic
{
    public BlockSpiritualEnergyCollector()
    {
        super(Material.wood);
        setBlockName(Strings.SPIRITUAL_ENERGY_COLLECTOR_NAME);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileSpiritualEnergyCollector();
    }
}
