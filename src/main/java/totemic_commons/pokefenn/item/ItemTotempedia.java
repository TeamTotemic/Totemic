package totemic_commons.pokefenn.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 19/02/14
 * Time: 12:49
 */
public class ItemTotempedia extends ItemTotemic
{

    public ItemTotempedia()
    {
        super(Strings.TOTEMPEDIA_NAME);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        player.openGui(Totemic.instance, Totemic.proxy.totempediaGuiID, world, 0, 0, 0);

        return stack;
    }

}
