package totemic_commons.pokefenn.depricated;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.fluid.ModFluids;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 13/02/14
 * Time: 12:48
 */
public class BlockChlorophyll extends BlockFluidClassic
{
    public BlockChlorophyll()
    {
        super(ModFluids.fluidChlorophyll, Material.water);
        setBlockName(Strings.FLUID_CHLOROPHYLL_NAME);
        setCreativeTab(Totemic.tabsTotem);

    }

    @SideOnly(Side.CLIENT)
    public static IIcon chlorophyllStillIcon;
    public static IIcon chlorophyllFlowingIcon;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        //chlorophyllFlowingIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.FLUID_CHLOROPHYLL_FLOWING);
        //chlorophyllStillIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.FLUID_CHLOROPHYLL_STILL);

        stack.getFluid().setIcons(chlorophyllStillIcon, chlorophyllFlowingIcon);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {

        if(side <= 1)
            return chlorophyllStillIcon;
        else
            return chlorophyllFlowingIcon;


    }
}
