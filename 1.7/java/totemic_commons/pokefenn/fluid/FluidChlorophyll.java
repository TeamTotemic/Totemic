package totemic_commons.pokefenn.fluid;


import net.minecraftforge.fluids.Fluid;

public class FluidChlorophyll extends Fluid
{

    public FluidChlorophyll(String fluidName)
    {
        super(fluidName);
        setDensity(1000);
        setViscosity(1000);
    }

    /*

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getStillIcon()
    {
        return BlockChlorophyll.chlorophyllStillIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getFlowingIcon()
    {
        return BlockChlorophyll.chlorophyllFlowingIcon;
    }
    */

}
