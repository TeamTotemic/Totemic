package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hinalib_commons.pokefenn.item.ItemNormal;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.GuiIds;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;
import totemic_commons.pokefenn.util.ItemStackNBTHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 04/12/13
 * Time: 17:45
 */
public class ItemPaintBrush extends ItemNormal {

    public ItemPaintBrush(int id){
        super(id);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.PAINT_BRUSH_NAME);
        setCreativeTab(Totemic.tabsTotem);


    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {

        itemIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.PAINT_BRUSH_ICON);


    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {


        if (!world.isRemote) {
            ItemStackNBTHelper.setBoolean(itemStack, Strings.NBT_ITEM_PAINT_BRUSH_GUI_OPEN, true);
            entityPlayer.openGui(Totemic.instance, GuiIds.PAINT_BRUSH, entityPlayer.worldObj, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
        }

        return itemStack;
    }

}
