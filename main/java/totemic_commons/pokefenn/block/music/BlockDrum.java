package totemic_commons.pokefenn.block.music;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.music.IMusicBlock;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockDrum extends BlockTileTotemic implements IMusicBlock
{
    public BlockDrum()
    {
        super(Material.wood);
        setBlockName(Strings.DRUM_NAME);
    }

    @Override
    public void playMusic(World world, int x, int y, int z)
    {

    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return null;
    }
}
