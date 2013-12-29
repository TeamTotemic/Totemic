package totemic_commons.pokefenn.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {


    public static Fluid fluidChlorophyll;

    public static void init() {

        fluidChlorophyll = new FluidChlorophyll();

        FluidRegistry.registerFluid(fluidChlorophyll);


    }

}
