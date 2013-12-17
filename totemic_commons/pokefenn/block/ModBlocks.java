package totemic_commons.pokefenn.block;

import net.minecraft.block.Block;
import totemic_commons.pokefenn.lib.BlockIds;
import totemic_commons.pokefenn.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	
	public static Block totemBase;
    public static Block bigBadTotemBase;
    public static Block bigBadTotemHead;
    public static Block chlorophyllSolidifier;
    public static Block plantShredder;
    public static Block venusFlyTrap;
    public static Block totemWoods;
	
	
	
	public static void init(){
		
		totemBase = new BlockTotemBase(BlockIds.TOTEM_BASE);
        //bigBadTotemBase = new BlockBigBadTotemBase(BlockIds.BIG_BAD_TOTEM_BASE);
        //bigBadTotemHead = new BlockBigBadTotemHead(BlockIds.BIG_BAD_TOTEM_HEAD);
        chlorophyllSolidifier = new BlockChlorophyllSolidifier(BlockIds.CHLOROPHYLL_SOLIDIFIER);
        //venusFlyTrap = new BlockVenusFlyTrap(BlockIds.VENUS_FLY_TRAP);
        totemWoods = new BlockTotemWoods(BlockIds.TOTEM_WOODS);


		
		
		GameRegistry.registerBlock(totemBase, Strings.TOTEM_BASE_NAME);
        //GameRegistry.registerBlock(bigBadTotemBase, Strings.BIG_BAD_TOTEM_BASE_NAME);
        //GameRegistry.registerBlock(bigBadTotemHead, Strings.BIG_BAD_TOTEM_HEAD_NAME);
        GameRegistry.registerBlock(chlorophyllSolidifier, Strings.CHLOROPHYLL_SOLIDIFIER_NAME);
        //GameRegistry.registerBlock(venusFlyTrap, Strings.VENUS_FLY_TRAP_NAME);
        GameRegistry.registerBlock(totemWoods, Strings.TOTEM_WOODS_NAME);

		
	}

}
