package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hinalib_commons.pokefenn.item.ItemNormal;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.block.ModBlocks;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 19/11/13
 * Time: 12:30
 */
public class ItemVenusFlyTrapSeed extends ItemNormal implements IPlantable {


    public ItemVenusFlyTrapSeed(int id){

        super(id);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.VENUS_FLY_TRAP_SEED_NAME);
        setMaxStackSize(64);
        setCreativeTab(Totemic.tabsTotem);


    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {

        itemIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.VENUS_FLY_TRAP_SEED_ICON);


    }


    @Override
    public EnumPlantType getPlantType(World world, int x, int y, int z) {
        return EnumPlantType.Crop;
    }

    @Override
    public int getPlantID(World world, int x, int y, int z) {
        return 0;
    }

    @Override
    public int getPlantMetadata(World world, int x, int y, int z) {
        return ModBlocks.venusFlyTrap.blockID;
    }
}
