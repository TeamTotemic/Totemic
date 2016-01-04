package totemic_commons.pokefenn.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTotemTorch;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockTotemTorch extends BlockTileTotemic
{
    public BlockTotemTorch()
    {
        super(Material.wood);
        setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.3F, 0.7F);
        setUnlocalizedName(Strings.TOTEM_TORCH_NAME);
        setLightLevel(1F);
        setStepSound(soundTypeWood);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        //FIXME
        /*for(int i = 0; i < 2; i++)
            world.spawnParticle("flame", x + 0.5, y + 1F, z + 0.5, 0, 0, 0);
        for(int i = 0; i < 16; i++)
            world.spawnParticle("smoke", x + 0.5, y + 1F, z + 0.5, 0, 0, 0);*/
    }

    //TODO: Replace TESR by JSON model
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
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileTotemTorch();
    }
}
