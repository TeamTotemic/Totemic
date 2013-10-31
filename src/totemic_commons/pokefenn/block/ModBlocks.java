package totemic_commons.pokefenn.block;

import net.minecraft.block.Block;
import totemic_commons.pokefenn.lib.BlockIds;
import totemic_commons.pokefenn.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	
	public static Block totemBase;
	
	
	
	public static void init(){
		
		totemBase = new BlockTotemBase(BlockIds.TOTEM_BASE);
		
		
		GameRegistry.registerBlock(totemBase, Strings.TOTEM_BASE_NAME);
		
		
	}

}
