package totemic_commons.pokefenn.fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraftforge.fluids.ItemFluidContainer;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 17/12/13
 * Time: 20:45
 */
public class ItemBottleChlorophyll extends ItemFluidContainer {

    public ItemBottleChlorophyll(int id)
    {

        super(id - 256);
        setCreativeTab(Totemic.tabsTotem);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.BOTTLE_CHLOROPHYLL_NAME);
        setMaxStackSize(16);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register)
    {

        itemIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.BOTTLE_CHLOROPHYLL_ICON);


    }


}
