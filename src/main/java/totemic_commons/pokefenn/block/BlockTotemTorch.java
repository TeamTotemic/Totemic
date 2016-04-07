package totemic_commons.pokefenn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockTotemTorch extends Block
{
    public BlockTotemTorch()
    {
        super(Material.wood);
        setBlockBounds(5F/16, 0.0F, 5F/16, 11F/16, 1.3F, 11F/16);
        setUnlocalizedName(Strings.TOTEM_TORCH_NAME);
        setLightLevel(1F);
        setStepSound(soundTypeWood);
        setHardness(0.05F);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        for(int i = 0; i < 2; i++)
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1F, pos.getZ() + 0.5, 0, 0, 0);
        for(int i = 0; i < 16; i++)
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5, pos.getY() + 1F, pos.getZ() + 0.5, 0, 0, 0);
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }
}
