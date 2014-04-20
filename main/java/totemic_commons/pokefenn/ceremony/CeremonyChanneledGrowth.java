package totemic_commons.pokefenn.ceremony;

import net.minecraft.block.BlockSapling;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.tileentity.TileCeremonyIntelligence;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyChanneledGrowth implements ICeremonyEffect
{
    @Override
    public void effect(TileCeremonyIntelligence tileCeremonyIntelligence)
    {
        /*

        World world = tileCeremonyIntelligence.getWorldObj();
        int x = tileCeremonyIntelligence.xCoord;
        int y = tileCeremonyIntelligence.yCoord;
        int z = tileCeremonyIntelligence.zCoord;

        int neededDrained = 10;

        Random random = new Random();

        if (world.blockExists(x + 3, y, z + 3) && world.getBlock(x + 3, y, z + 3) instanceof IPlantable)
        {
            if (tileCeremonyIntelligence.canDoEffectOverall(neededDrained))
            {
                world.getBlock(x + 3, y, z + 3).updateTick(world, x + 3, y, z + 3, random);
                tileCeremonyIntelligence.drainEssence(10);
            }
        }

        if (world.blockExists(x - 3, y, z - 3) && world.getBlock(x - 3, y, z - 3) instanceof IPlantable)
        {
            if (tileCeremonyIntelligence.canDoEffectOverall(neededDrained))
            {
                world.getBlock(x - 3, y, z - 3).updateTick(world, x - 3, y, z - 3, random);
                tileCeremonyIntelligence.drainEssence(10);
            }
        }

        if (world.blockExists(x - 3, y, z + 3) && world.getBlock(x - 3, y, z + 3) instanceof IPlantable)
        {
            if (tileCeremonyIntelligence.canDoEffectOverall(neededDrained))
            {
                world.getBlock(x - 3, y, z + 3).updateTick(world, x - 3, y, z + 3, random);
                tileCeremonyIntelligence.drainEssence(10);
            }
        }

        if (world.blockExists(x + 3, y, z - 3) && world.getBlock(x + 3, y, z - 3) instanceof IPlantable)
        {
            if (tileCeremonyIntelligence.canDoEffectOverall(neededDrained))
            {
                world.getBlock(x + 3, y, z - 3).updateTick(world, x + 3, y, z - 3, random);
                tileCeremonyIntelligence.drainEssence(10);
            }
        }
        */
    }
}
