package totemic_commons.pokefenn.world;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 16/02/14
 * Time: 17:25
 */
public class TotemicWorldGeneration implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        int x = chunkX * 16 + random.nextInt(16);
        int z = chunkZ * 16 + random.nextInt(16);

        if(ConfigurationSettings.GENERATE_TOTEM_TREES)
        {
            if(random.nextInt(1500) < 20)
            {
                //new TotemTreeGeneration().generate(world, random, x, random.nextInt(3) + 4, z);
            }

        }


    }

}