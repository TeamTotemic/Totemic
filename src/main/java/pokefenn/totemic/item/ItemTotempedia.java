package pokefenn.totemic.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.lexicon.ILexicon;
import pokefenn.totemic.lib.Strings;

public class ItemTotempedia extends ItemTotemic implements ILexicon
{
    public ItemTotempedia()
    {
        super(Strings.TOTEMPEDIA_NAME);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        player.openGui(Totemic.instance, 0, world, 0, 0, 0);

        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}
