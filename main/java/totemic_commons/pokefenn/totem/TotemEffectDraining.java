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

    public void effect(TileTotemic totem, int upgrades, boolean intelligence, TotemRegistry totemRegistry)
    {
        if (totem.getWorldObj().getWorldTime() % 100L == 0)
            drainEffect((TileTotemIntelligence) totem, upgrades);
    }

    protected static void drainEffect(TileTotemIntelligence tileTotemIntelligence, int upgrades)
    {
        int totemRadius = ConfigurationSettings.TOTEM_DRAINING_RANGE + (upgrades * 3);

        for (int i = -totemRadius; i <= totemRadius; i++)
            for (int j = -totemRadius; j <= totemRadius; j++)
                reducePlantMetadata(tileTotemIntelligence, tileTotemIntelligence.xCoord + i, tileTotemIntelligence.yCoord, tileTotemIntelligence.zCoord + j);
    }

    protected static void reducePlantMetadata(TileTotemIntelligence tileTotemIntelligence, int x, int y, int z)
    {
        //yCoords is there because the totem has to be on the same level as the IPlantable's
        Block blockQuery = tileTotemIntelligence.getWorldObj().getBlock(x, y, z);
        boolean isNotFlower = !blockQuery.getUnlocalizedName().contains("flower");
        boolean isNotBush = !blockQuery.getUnlocalizedName().contains("bush");
        boolean isNotBerry = !blockQuery.getUnlocalizedName().contains("berry");
        boolean isNotKelp = !blockQuery.getUnlocalizedName().contains("kelp");

        if (blockQuery instanceof IPlantable && tileTotemIntelligence.getWorldObj().getBlockMetadata(x, y, z) >= 4 && tileTotemIntelligence.getStackInSlot(TileTotemIntelligence.SLOT_ONE) != null && !(blockQuery instanceof IBlacklistedDraining) && isNotFlower && isNotBush && isNotBerry && isNotKelp)
        {
            tileTotemIntelligence.getWorldObj().setBlockMetadataWithNotify(x, y, z, tileTotemIntelligence.getWorldObj().getBlockMetadata(x, y, z) - 1, 2);
            tileTotemIntelligence.increaseChlorophyll(blockQuery);
        }
    }

}
