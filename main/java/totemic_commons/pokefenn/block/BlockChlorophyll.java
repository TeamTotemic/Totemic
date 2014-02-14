package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.fluid.ModFluids;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 13/02/14
 * Time: 12:48
 */
public class BlockChlorophyll extends BlockFluidClassic
{
    public BlockChlorophyll(int id)
    {
        super(id, ModFluids.fluidChlorophyll, Material.water);
        setUnlocalizedName(Strings.FLUID_CHLOROPHYLL_NAME);
        setCreativeTab(Totemic.tabsTotem);

    }

    @SideOnly(Side.CLIENT)
    public static Icon chlorophyllStillIcon;
    public static Icon chlorophyllFlowingIcon;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register){
        chlorophyllFlowingIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.FLUID_CHLOROPHYLL_FLOWING);
        chlorophyllStillIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.FLUID_CHLOROPHYLL_STILL);

        stack.getFluid().setIcons(chlorophyllStillIcon, chlorophyllFlowingIcon);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int meta) {

        if (side <= 1)
            return chlorophyllStillIcon;
        else
            return chlorophyllFlowingIcon;


    }
}
