package totemic_commons.pokefenn.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockBroken
{

    @SubscribeEvent()
    public void onBlockBroken(BlockEvent.BreakEvent breakEvent)
    {
        if(breakEvent != null)
        {
            if(breakEvent.block == ModBlocks.tipi)
            {
                breakUnderTipi(breakEvent.world, breakEvent.x, breakEvent.y + 6, breakEvent.z);
            }

            if(breakEvent.block == ModBlocks.dummyTipi)
            {
                int range = 5;
                int vertRange = 7;
                for(int i = -range; i <= range; i++)
                    for(int j = -vertRange; j <= vertRange; j++)
                        for(int k = -range; k <= range; k++)
                            if(breakEvent.world.getBlock(breakEvent.x + i, breakEvent.y + j, breakEvent.z + k) == ModBlocks.dummyTipi && breakEvent.world.getBlockMetadata(breakEvent.x + i, breakEvent.y + j, breakEvent.z + k) == 1)
                            {
                                //Break all blocks under here, need to do this later.
                                EntityUtil.spawnEntityInWorld(breakEvent.world, breakEvent.x + i, breakEvent.y + j, breakEvent.z + k, ModItems.tipi);
                                breakEvent.world.setBlockToAir(breakEvent.x + i, breakEvent.y + j, breakEvent.z + k);
                                breakUnderTipi(breakEvent.world, breakEvent.x + i, breakEvent.y + j, breakEvent.z + k);
                                break;
                            }

            }
        }
    }

    public static void breakUnderTipi(World world, int x, int y, int z)
    {
        int vertRadius = 7;
        int radius = 5;
        for(int i = -radius; i <= radius; i++)
            for(int j = -vertRadius; j <= vertRadius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    int n = j * -1;

                    if(world.getBlock(x + i, y + n, z + k) != null)
                    {
                        Block block = world.getBlock(x + i, y + n, z + k);

                        if(block == ModBlocks.dummyTipi || block == ModBlocks.tipi)
                        {
                            world.setBlockToAir(x + i, y + n, z + k);
                        }
                    }
                }
    }

}
