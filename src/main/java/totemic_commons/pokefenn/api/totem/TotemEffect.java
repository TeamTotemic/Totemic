package totemic_commons.pokefenn.api.totem;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TotemEffect
{
    protected final String name;

    /**
     * @param name a unique name for the Totem Effect
     */
    public TotemEffect(String name)
    {
        this.name = name;
    }

    /**
     * Performs the totem effect at the given Totem base position.<p>
     * This gets called on the server and the client.
     * @param totem the Totem Base tile entity
     * @param repetition how many Totem Pole blocks in the pole are carved with this effect
     */
    public abstract void effect(World world, BlockPos pos, TotemBase totem, int repetition);

    /**
     * @return the Totem Effect's name
     */
    public final String getName()
    {
        return name;
    }

    /**
     * @return the unlocalized name of the Effect, which by default
     * is given by "totemic.totem." followed by the name
     */
    public String getUnlocalizedName()
    {
        return "totemic.totem." + name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
