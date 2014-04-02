package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
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

    public ItemTotempedia(int id)
    {
        super(id);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TOTEMPEDIA_NAME);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick (ItemStack stack, World world, EntityPlayer player)
    {
        player.openGui(Totemic.instance, Totemic.proxy.totempediaGuiID, world, 0, 0, 0);

        return stack;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("totemic:totempedia");
    }

}
