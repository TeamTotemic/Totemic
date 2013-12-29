package totemic_commons.pokefenn.fluid;


import net.minecraftforge.fluids.Fluid;
import totemic_commons.pokefenn.lib.Strings;

public class FluidChlorophyll extends Fluid {

    public FluidChlorophyll() {

        super(Strings.FLUID_CHLOROPHYLL_NAME);
        setDensity(100);
        setViscosity(100);

    }

}
