package totemic_commons.pokefenn.fluid;


import totemic_commons.pokefenn.lib.Strings;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidChlorophyll extends Fluid {
	
	public FluidChlorophyll() {
		
		
		super(Strings.FLUID_CHLOROPHYLL_NAME);
		setDensity(100);
        setViscosity(100);
        FluidRegistry.registerFluid(this);
		
	}

}
