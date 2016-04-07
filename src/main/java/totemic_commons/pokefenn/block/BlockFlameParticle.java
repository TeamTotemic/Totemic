package totemic_commons.pokefenn.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn. Licensed under MIT (If this is one of my Mods)
 */
public class BlockFlameParticle extends Block
{
    public BlockFlameParticle()
    {
        super(Material.rock);
        setUnlocalizedName(Strings.FLAME_PARTICLE_NAME);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
        int currentInput = world.getRedstonePower(pos, null);

        if(currentInput == 0)
            for(int i = 0; i < 5; i++)
            {
                float randomX = rand.nextFloat();
                float randomZ = rand.nextFloat();
                for(int k = 0; k <= 15; k++)
                {
                    world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + randomX, pos.getY() + (rand.nextFloat() * k), pos.getZ() + randomZ, 0, 0.15, 0);
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + randomX, pos.getY() + (rand.nextFloat() * k), pos.getZ() + randomZ, 0, 0.15, 0);
                }
            }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity)
    {
        if(!entity.isImmuneToFire())
            if(!entity.isBurning())
                entity.setFire(15);
    }

}
