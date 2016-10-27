package totemic_commons.pokefenn.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;
import vazkii.botania.totemic_custom.api.lexicon.ILexicon;

public class ItemTotempedia extends ItemTotemic implements ILexicon
{

    public ItemTotempedia()
    {
        super(Strings.TOTEMPEDIA_NAME);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        player.openGui(Totemic.instance, 0, world, 0, 0, 0);

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

}
