package totemic_commons.pokefenn;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import totemic_commons.pokefenn.block.*;
import totemic_commons.pokefenn.lib.BlockIds;
import totemic_commons.pokefenn.lib.Strings;

public final class ModBlocks
{


    public static Block totemBase;
    public static Block chlorophyllSolidifier;
    public static Block plantShredder;
    public static Block venusFlyTrap;
    public static Block totemWoods;
    public static Block totemDraining;
    public static Block totemTable;


    public static void init()
    {

        totemBase = new BlockTotemBase(BlockIds.TOTEM_BASE);
        chlorophyllSolidifier = new BlockChlorophyllSolidifier(BlockIds.CHLOROPHYLL_SOLIDIFIER);
        //venusFlyTrap = new BlockVenusFlyTrap(BlockIds.VENUS_FLY_TRAP);
        totemWoods = new BlockTotemWoods(BlockIds.TOTEM_WOODS);
        totemDraining = new BlockTotemDraining(BlockIds.TOTEM_DRAINING);
        totemTable = new BlockTotemTable(BlockIds.TOTEM_TABLE);


        GameRegistry.registerBlock(totemBase, Strings.TOTEM_BASE_NAME);
        GameRegistry.registerBlock(chlorophyllSolidifier, Strings.CHLOROPHYLL_SOLIDIFIER_NAME);
        //GameRegistry.registerBlock(venusFlyTrap, Strings.VENUS_FLY_TRAP_NAME);
        GameRegistry.registerBlock(totemWoods, Strings.TOTEM_WOODS_NAME);
        GameRegistry.registerBlock(totemDraining, Strings.TOTEM_DRAINING_NAME);
        GameRegistry.registerBlock(totemTable, Strings.TOTEM_TABLE_NAME);


    }

}
