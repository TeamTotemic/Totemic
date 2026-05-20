package totemic_commons.pokefenn.ceremony;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;

public class CeremonyFertility extends Ceremony
{
    //Weak set to keep track which villagers have been affected by the ceremony
    private static final Set<EntityVillager> matedVillagers = Collections.newSetFromMap(new WeakHashMap<>());

    public CeremonyFertility(String modid, String name, int musicNeeded, int maxStartupTime, int effectTime, int musicPer5, MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, effectTime, musicPer5, instruments);
    }

    @Override
    public void effect(World world, int x, int y, int z)
    {
        if(world.getTotalWorldTime() % 20 == 0)
        {
            transformSaplings(world, x, y, z);
            breedAnimalsAndVillagers(world, x, y, z);
        }
    }

    private void transformSaplings(World world, int x, int y, int z)
    {
        final int radius = 6;
        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    Block block = world.getBlock(x + i, y + j, z + k);
                    if(block instanceof BlockSapling && block != ModBlocks.totemSapling)
                    {
                        world.setBlock(x + i, y + j, z + k, ModBlocks.totemSapling, 0, 3);
                        spawnParticles(world, x + 0.5, y + 0.5, z + 0.5);
                        return;
                    }
                }
    }

    private void breedAnimalsAndVillagers(World world, int x, int y, int z)
    {
        // TODO Auto-generated method stub
    }

    private void spawnParticles(World world, double x, double y, double z)
    {
        if(world.isRemote)
        {
            double dx = world.rand.nextGaussian();
            double dy = world.rand.nextGaussian() * 0.5;
            double dz = world.rand.nextGaussian();
            double velY = world.rand.nextGaussian();
            for(int i = 0; i < 10; i++)
                world.spawnParticle("happyVillager", x + dx, y + dy, z + dz, 0, velY, 0);
        }
    }
}
