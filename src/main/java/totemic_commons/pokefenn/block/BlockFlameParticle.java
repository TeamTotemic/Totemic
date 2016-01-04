package totemic_commons.pokefenn.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockFlameParticle extends Block
{
    public BlockFlameParticle()
    {
        super(Material.rock);
        setUnlocalizedName(Strings.FLAME_PARTICLE_NAME);
        setCreativeTab(Totemic.tabsTotem);
    }

    /*@Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        int currentInput = world.getRedstonePower(pos, null);

        if(currentInput == 0)
            for(int i = 0; i < 5; i++)
            {
                float random = rand.nextFloat();
                for(int k = 0; k <= 15; k++)
                {
                    //FIXME: World.spawnParticle changed signature
                    world.spawnParticle("flame", pos.getX() + random, pos.getY() + (rand.nextFloat() * k), pos.getZ() + random, 0, 0.15, 0);
                    world.spawnParticle("smoke", x + random, y + (rand.nextFloat() * k), z + random, 0, 0.15, 0);
                }
            }
    }*/

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity)
    {
        if(!entity.isImmuneToFire())
            if(!entity.isBurning())
                entity.setFire(15);
    }

}
