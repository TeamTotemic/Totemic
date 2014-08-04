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
     * @param socketAmount  The amount of sockets the Totem has above it.
     * @param totemRegistry The TotemRegistry, use it to gather information about the Totem.
     * @param horizontal    The Horizontal radius.
     * @param vertical      The Vertical radius.
     * @param melodyAmount  The amount of musical melody the totem pole has, this is used to determine how strong a effect is.
     */
    public void effect(TileEntity totem, int socketAmount, TotemRegistry totemRegistry, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus);
}
