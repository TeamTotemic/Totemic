package totemic_commons.pokefenn.api.totem;

import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.api.recipe.TotemRegistry;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/01/14
 * Time: 13:52
 */
public interface ITotemEffect
{
    /**
     * @param totem         The tile entity of the Totem.
     * @param intelligence  Does it run off plant essence?
     * @param socketAmount  The amount of sockets the Totem has above it.
     * @param totemRegistry The TotemRegistry, use it to gather information about the Totem.
     * @param horizontal    The Horizontal radius.
     * @param verticle      The Vertical radius.
     */
    public void effect(TileEntity totem, int socketAmount, boolean intelligence, TotemRegistry totemRegistry, int horizontal, int verticle);
}
