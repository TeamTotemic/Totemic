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
public class ItemTotempedia extends ItemNormal
{

    public ItemTotempedia(int id)
    {
        super(id);
        setUnlocalizedName(Strings.TOTEMPEDIA_NAME);
        setMaxStackSize(1);
    }

    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if(itemStack != null && world != null)
        {
            player.openGui(Totemic.instance, 0, world, par4, par5, par6);

        }

        return !player.isSneaking();
    }



    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("totemic:totempedia");
    }

}
