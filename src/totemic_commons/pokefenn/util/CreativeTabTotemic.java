package totemic_commons.pokefenn.util;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabTotemic extends CreativeTabs {
	    
	    
	    public CreativeTabTotemic(int par1, String par2Str) {
	        
	        super(par1, par2Str);
	    }

	    @Override
	    @SideOnly(Side.CLIENT)
	    public int getTabIconItemIndex() {
	        
	        return Block.wood.blockID;
	    }

}
