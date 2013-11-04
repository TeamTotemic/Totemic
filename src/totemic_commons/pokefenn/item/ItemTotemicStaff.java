package totemic_commons.pokefenn.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTotemicStaff extends ItemTotemic {
	
	
	public ItemTotemicStaff(int id){
		
		super(id);
        this.setUnlocalizedName(Strings.TOTEMIC_STAFF_NAME);
        setMaxStackSize(64);
        
		
		
	}
	
	
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
    	
    	itemIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.TOTEMIC_STAFF_ICON);       
    	
    	
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("A staff for all your totemic needs!");
    }

}
