package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;

import java.util.List;

public class ItemTotemWhittlingKnife extends ItemTotemic {
	
	
	public ItemTotemWhittlingKnife(int id) {
	
	super(id);
    this.setUnlocalizedName(Strings.TOTEM_WHITTLING_KNIFE_NAME);
    this.setMaxStackSize(1);

}

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
    	
    	itemIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.TOTEM_WHITTLING_KNIFE_ICON);       
    	
    	
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("A knife to whittl y'self a totem!");
    }

}
	

