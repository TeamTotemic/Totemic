package totemic_commons.pokefenn.world;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 16/02/14
 * Time: 17:25
 */
public class TotemicWorldGeneration implements IWorldGenerator
{
    private static List<Integer> _blacklistedDimensions;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if(_blacklistedDimensions == null)
        {
            _blacklistedDimensions = buildBlacklistedDimensions();
        }

        if(_blacklistedDimensions.contains(world.provider.dimensionId))
        {
            return;
        }

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

    private static List<Integer> buildBlacklistedDimensions()
    {
        String blacklist = ConfigurationSettings.TOTEM_TREE_GENERATION_BLACKLIST;
        List<Integer> dims = new ArrayList<Integer>();

        if(blacklist == null)
        {
            return dims;
        }
        blacklist = blacklist.trim();

        for(String dim : blacklist.split(","))
        {
            try
            {
                Integer dimId = Integer.parseInt(dim);
                dims.add(dimId);
            } catch(Exception x)
            {
            }
        }

        return dims;
    }

}