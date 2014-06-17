package totemic_commons.pokefenn.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;
import vazkii.botania.totemic_custom.api.lexicon.ILexicon;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 19/02/14
 * Time: 12:49
 */
public class ItemTotempedia extends ItemTotemic implements ILexicon
{

    public ItemTotempedia()
    {
        super(Strings.TOTEMPEDIA_NAME);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        player.openGui(Totemic.instance, 0, world, 0, 0, 0);

        return itemStack;
    }

}
