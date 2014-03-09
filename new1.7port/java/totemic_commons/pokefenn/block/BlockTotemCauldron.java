package totemic_commons.pokefenn.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTotemCauldron;


public class BlockTotemCauldron extends BlockTileTotemic
{
    public BlockTotemCauldron()
    {
        super(Material.rock);
        setBlockName(Strings.TOTEM_CAULDRON_NAME);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileTotemCauldron();
    }
}
