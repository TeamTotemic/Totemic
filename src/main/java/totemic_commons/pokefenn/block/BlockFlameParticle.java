package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockFlameParticle extends Block
{
    public BlockFlameParticle()
    {
        super(Material.rock);
        setBlockName(Strings.FLAME_PARTICLE_NAME);
        setCreativeTab(Totemic.tabsTotem);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        int currentInput = world.getBlockPowerInput(x, y, z);

        if(currentInput == 0)
            for(int i = 0; i < 5; i++)
            {
                float random = rand.nextFloat();
                for(int k = 0; k <= 15; k++)
                {
                    world.spawnParticle("flame", x + random, y + (rand.nextFloat() * k), z + random, 0, 0.15, 0);
                    world.spawnParticle("smoke", x + random, y + (rand.nextFloat() * k), z + random, 0, 0.15, 0);
                }
            }
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int Z, Entity entity)
    {
        if(!world.isRemote)
            if(!entity.isImmuneToFire())
                if(!entity.isBurning())
                    entity.setFire(20);
    }

}
