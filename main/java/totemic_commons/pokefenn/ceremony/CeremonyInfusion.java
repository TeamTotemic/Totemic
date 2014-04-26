package totemic_commons.pokefenn.ceremony;

import net.minecraft.block.BlockSapling;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.tileentity.totem.TileCeremonyIntelligence;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyInfusion implements ICeremonyEffect
{
    @Override
    public void effect(TileCeremonyIntelligence tileCeremonyIntelligence)
    {
        World world = tileCeremonyIntelligence.getWorldObj();
        int x = tileCeremonyIntelligence.xCoord;
        int y = tileCeremonyIntelligence.yCoord;
        int z = tileCeremonyIntelligence.zCoord;

        int neededDrained = 100;

        if(world.blockExists(x - 3, y, z + 3) && world.getBlock(x + 3, y, z + 3) instanceof BlockSapling)
        {
            if(tileCeremonyIntelligence.overallDrained >= neededDrained)
            {
                world.setBlock(x + 3, y, z + 3, ModBlocks.totemSapling);
            }
        }

        if(world.blockExists(x - 3, y, z + 3) && world.getBlock(x + 3, y, z + 3) instanceof BlockSapling)
        {
            if(tileCeremonyIntelligence.overallDrained >= neededDrained)
            {
                world.setBlock(x - 3, y, z - 3, ModBlocks.totemSapling);
            }
        }

        if(world.blockExists(x - 3, y, z + 3) && world.getBlock(x + 3, y, z + 3) instanceof BlockSapling)
        {
            if(tileCeremonyIntelligence.overallDrained >= neededDrained)
            {
                world.setBlock(x - 3, y, z + 3, ModBlocks.totemSapling);
            }
        }

        if(world.blockExists(x - 3, y, z + 3) && world.getBlock(x + 3, y, z + 3) instanceof BlockSapling)
        {
            if(tileCeremonyIntelligence.overallDrained >= neededDrained)
            {
                world.setBlock(x + 3, y, z - 3, ModBlocks.totemSapling);
            }
        }


    }
}
