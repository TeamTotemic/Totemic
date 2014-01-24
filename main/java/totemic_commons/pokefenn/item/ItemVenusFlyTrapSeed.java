package totemic_commons.pokefenn.item;

import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import rukalib_commons.pokefenn.item.ItemNormal;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 19/11/13
 * Time: 12:30
 */
public class ItemVenusFlyTrapSeed extends ItemNormal implements IPlantable
{


    public ItemVenusFlyTrapSeed(int id)
    {

        super(id);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.VENUS_FLY_TRAP_SEED_NAME);
        setMaxStackSize(64);
        setCreativeTab(Totemic.tabsTotem);

    }


    @Override
    public EnumPlantType getPlantType(World world, int x, int y, int z)
    {
        return EnumPlantType.Crop;
    }

    @Override
    public int getPlantID(World world, int x, int y, int z)
    {
        return ModBlocks.venusFlyTrap.blockID;
    }

    @Override
    public int getPlantMetadata(World world, int x, int y, int z)
    {
        return 0;
    }
}
