package totemic_commons.pokefenn.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileSpiritualEnergyMover;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockSpiritualEnergyMover extends BlockTileTotemic
{
    public BlockSpiritualEnergyMover()
    {
        super(Material.wood);
        setBlockName(Strings.SPIRITUAL_ENERGY_MOVER_NAME);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileSpiritualEnergyMover();
    }
}
