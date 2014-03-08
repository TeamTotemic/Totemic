package totemic_commons.pokefenn.totem;

import net.minecraft.block.Block;
import net.minecraftforge.common.IPlantable;
import totemic_commons.pokefenn.api.IBlacklistedDraining;
import totemic_commons.pokefenn.api.ITotemEffect;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemic;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 18/02/14
 * Time: 10:47
 */
public class TotemEffectDraining implements ITotemEffect
{


    public static void effect(TileTotemic tileTotemIntelligence, int i, int upgrades)
    {
        if (tileTotemIntelligence.worldObj.getWorldTime() % 100L == 0)
        {
            //System.out.println(":(");
            drainEffect((TileTotemIntelligence) tileTotemIntelligence, i, upgrades);
        }

    }

    protected static void drainEffect(TileTotemIntelligence tileTotemIntelligence, int k, int upgrades)
    {
        int totemRadius = ConfigurationSettings.TOTEM_DRAINING_RANGE + (upgrades * 3);

        for (int i = -totemRadius; i <= totemRadius; i++)
        {
            for (int j = -totemRadius; j <= totemRadius; j++)
            {
                //System.out.println("AH");
                reducePlantMetadata(tileTotemIntelligence, k, tileTotemIntelligence.xCoord + i, tileTotemIntelligence.yCoord, tileTotemIntelligence.zCoord + j);
            }
        }

    }

    protected static void reducePlantMetadata(TileTotemIntelligence tileTotemIntelligence, int i, int x, int y, int z)
    {
        //yCoords is there because the totem has to be on the same level as the IPlantable's
        Block blockQuery = Block.blocksList[tileTotemIntelligence.worldObj.getBlockId(x, y, z)];

        if (tileTotemIntelligence.worldObj.getBlockMetadata(x, y, z) >= 5 && blockQuery instanceof IPlantable && tileTotemIntelligence.getStackInSlot(TileTotemIntelligence.SLOT_ONE) != null && !(blockQuery instanceof IBlacklistedDraining))
        {
            tileTotemIntelligence.worldObj.setBlockMetadataWithNotify(x, y, z, tileTotemIntelligence.worldObj.getBlockMetadata(x, y, z) - 1, 2);
            tileTotemIntelligence.increaseChlorophyll();
        }
    }

}
