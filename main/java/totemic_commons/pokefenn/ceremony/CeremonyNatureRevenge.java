package totemic_commons.pokefenn.ceremony;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.tileentity.TileCeremonyIntelligence;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyNatureRevenge implements ICeremonyEffect
{
    @Override
    public void effect(TileCeremonyIntelligence tileCeremonyIntelligence)
    {
        int x = tileCeremonyIntelligence.xCoord;
        int y = tileCeremonyIntelligence.yCoord;
        int z = tileCeremonyIntelligence.zCoord;
        int totemRadius = 16;
        int yAxisRadius = 4;

        for(int i = -totemRadius; i <= totemRadius; i++)
            for(int k = -totemRadius; k <= yAxisRadius; k++)
            {
                Block block = tileCeremonyIntelligence.getWorldObj().getBlock(x + i, y, z + k);
                Block blockUnder = tileCeremonyIntelligence.getWorldObj().getBlock(x + i, -y, z + k);
                Random rand = new Random();
                int random = rand.nextInt(8);

                if(blockUnder.canSustainPlant(tileCeremonyIntelligence.getWorldObj(), x + i, y, z + k, ForgeDirection.UP, (IPlantable) returnBlock(random)))
                {
                    tileCeremonyIntelligence.getWorldObj().setBlock(x + i, y, z + k, returnBlock(random), rand.nextInt(4), 2);
                }

            }
        if(tileCeremonyIntelligence.getWorldObj().getWorldTime() % 20L == 0L)
        {
            if(EntityUtil.getEntitiesInRange(tileCeremonyIntelligence.getWorldObj(), x, y, z, 16, 16) != null) ;
            {
                for(Entity entity : (EntityUtil.getEntitiesInRange(tileCeremonyIntelligence.getWorldObj(), x, y, z, 16, 16)))
                {
                    Random rand = new Random();
                    if(entity instanceof EntityPlayer && rand.nextBoolean())
                    {
                        entity.attackEntityFrom(DamageSource.generic, 4);
                    }
                }
            }
        }
    }

    public Block returnBlock(int i)
    {
        if(i == 0)
            return Blocks.wheat;
        if(i == 1)
            return Blocks.melon_stem;
        if(i == 2)
            return Blocks.pumpkin_stem;
        if(i == 3)
            return Blocks.carrots;
        if(i == 4)
            return Blocks.potatoes;
        if(i == 5)
            return ModBlocks.moonglow;
        if(i == 6)
            return ModBlocks.bloodwart;
        if(i == 7)
            return ModBlocks.lotusBlock;
        if(i == 8)
            return ModBlocks.fungusBlock;

        return null;
    }
}
