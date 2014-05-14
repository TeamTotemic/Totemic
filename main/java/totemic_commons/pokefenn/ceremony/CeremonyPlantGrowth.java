package totemic_commons.pokefenn.ceremony;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.tileentity.totem.TileCeremonyIntelligence;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyPlantGrowth implements ICeremonyEffect
{
    @Override
    public void effect(TileEntity tileEntity)
    {
        TileCeremonyIntelligence tileCeremonyIntelligence = (TileCeremonyIntelligence) tileEntity;

        World world = tileCeremonyIntelligence.getWorldObj();

        if(tileCeremonyIntelligence != null)
        {
            int radius = 12;
            int yRadius = 5;

            int x = tileCeremonyIntelligence.xCoord;
            int y = tileCeremonyIntelligence.yCoord;
            int z = tileCeremonyIntelligence.zCoord;

            for(int i = -radius; i <= radius; i++)
                for(int j = -yRadius; j <= yRadius; j++)
                    for(int k = -radius; k <= radius; k++)
                    {
                        if(world.getBlock(x + i, y + j, z + k) != null)
                        {
                            Block block = world.getBlock(x + i, y + j, z + k);

                            if(block instanceof IPlantable)
                            {
                                Random rand = new Random();
                                if(world.getWorldTime() % 20L == 0)
                                    block.updateTick(world, x + i, y + j, z + k, rand);
                            }
                        }
                    }
        }
    }
}
