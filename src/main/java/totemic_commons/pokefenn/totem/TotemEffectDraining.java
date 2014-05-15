package totemic_commons.pokefenn.totem;

import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.IPlantable;
import totemic_commons.pokefenn.api.IBlacklistedDraining;
import totemic_commons.pokefenn.api.totem.ITotemEffect;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.recipe.registry.TotemRegistry;
import totemic_commons.pokefenn.tileentity.totem.TileTotemIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemic;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 18/02/14
 * Time: 10:47
 */
public class TotemEffectDraining implements ITotemEffect
{

    public void effect(TileTotemic totem, int upgrades, boolean intelligence, TotemRegistry totemRegistry, int horizontal, int verticle, int melody)
    {
        if(totem.getWorldObj().getWorldTime() % 80L == 0)
            drainEffect((TileTotemIntelligence) totem, upgrades, melody);
    }

    protected static void drainEffect(TileTotemIntelligence tileTotemIntelligence, int upgrades, int melody)
    {
        int totemRadius = 8 + (upgrades * 3);

        for(int i = -totemRadius; i <= totemRadius; i++)
            for(int j = -totemRadius; j <= totemRadius; j++)
            {
                reducePlantMetadata(tileTotemIntelligence, tileTotemIntelligence.xCoord + i, tileTotemIntelligence.yCoord, tileTotemIntelligence.zCoord + j, melody);
            }
    }

    protected static void reducePlantMetadata(TileTotemIntelligence tileTotemIntelligence, int x, int y, int z, int melody)
    {
        //yCoords is there because the totem has to be on the same level as the IPlantable's
        Block blockQuery = tileTotemIntelligence.getWorldObj().getBlock(x, y, z);
        boolean isNotFlower = !blockQuery.getUnlocalizedName().contains("flower");
        boolean isNotBush = !blockQuery.getUnlocalizedName().contains("bush");
        boolean isNotBerry = !blockQuery.getUnlocalizedName().contains("berry");
        boolean isNotKelp = !blockQuery.getUnlocalizedName().contains("kelp");

        if(blockQuery != null && blockQuery instanceof IPlantable)
        {
            if(tileTotemIntelligence.getWorldObj().getBlockMetadata(x, y, z) >= 3 && !(blockQuery instanceof IBlacklistedDraining) && isNotFlower && isNotBush && isNotBerry && isNotKelp)
            {

                Random rand = new Random();

                if(rand.nextInt(4) == 1)
                    tileTotemIntelligence.getWorldObj().setBlockMetadataWithNotify(x, y, z, tileTotemIntelligence.getWorldObj().getBlockMetadata(x, y, z) - 1, 2);
                tileTotemIntelligence.increasePlantEssence(blockQuery);
                MinecraftServer.getServer().worldServerForDimension(tileTotemIntelligence.getWorldObj().provider.dimensionId).func_147487_a("happyVillager", (double) x + 0.5D, (double) y + 0.9D, (double) z + 0.5D, 4, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

}
