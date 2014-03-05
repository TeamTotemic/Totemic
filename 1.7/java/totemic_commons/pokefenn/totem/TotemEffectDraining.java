package totemic_commons.pokefenn.totem;

import net.minecraft.block.Block;
import net.minecraftforge.common.IPlantable;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.ITotemEffect;
import totemic_commons.pokefenn.client.ParticleUtil;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.lib.Particles;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 18/02/14
 * Time: 10:47
 */
public class TotemEffectDraining implements ITotemEffect
{
    public static int totemRadius = ConfigurationSettings.TOTEM_DRAINING_RANGE;


    public static void effect(TileTotemIntelligence tileTotemIntelligence, int i)
    {
        if (tileTotemIntelligence.getWorldObj().getWorldTime() % 100L == 0)
        {
            drainEffect(tileTotemIntelligence, i);
        }

    }

    protected static void drainEffect(TileTotemIntelligence tileTotemIntelligence, int k)
    {

        for (int i = -totemRadius; i <= totemRadius; i++)
        {
            for (int j = -totemRadius; j <= totemRadius; j++)
            {
                reducePlantMetadata(tileTotemIntelligence, k, tileTotemIntelligence.xCoord + i, tileTotemIntelligence.yCoord, tileTotemIntelligence.zCoord + j);
            }
        }

    }

    protected static void reducePlantMetadata(TileTotemIntelligence tileTotemIntelligence, int i, int x, int y, int z)
    {
        //yCoords is there because the totem has to be on the same level as the IPlantable's
        Block blockQuery = tileTotemIntelligence.getWorldObj().getBlock(x, y, z);

        if (tileTotemIntelligence.getWorldObj().getBlockMetadata(x, y, z) >= 5 && blockQuery instanceof IPlantable && tileTotemIntelligence.getStackInSlot(tileTotemIntelligence.SLOT_ONE) != null)
        {
            if (tileTotemIntelligence.getStackInSlot(tileTotemIntelligence.SLOT_ONE).getItem() == ModItems.chlorophyllCrystal || tileTotemIntelligence.getStackInSlot(tileTotemIntelligence.SLOT_ONE).getItem() == ModItems.blazingChlorophyllCrystal)
            {
                ParticleUtil.spawnParticle(tileTotemIntelligence.getWorldObj(), Particles.ESSENCE_DRAIN, x, y, z, 10, 10, 10);
                tileTotemIntelligence.getWorldObj().setBlockMetadataWithNotify(x, y, z, tileTotemIntelligence.getWorldObj().getBlockMetadata(x, y, z) - 1, 2);
                tileTotemIntelligence.increaseChlorophyll(i);
            }
        }
    }

}
