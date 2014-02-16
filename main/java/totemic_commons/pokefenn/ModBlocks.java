package totemic_commons.pokefenn;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import totemic_commons.pokefenn.block.*;
import totemic_commons.pokefenn.lib.BlockIds;
import totemic_commons.pokefenn.lib.Strings;

public final class ModBlocks
{

    public static Block chlorophyllSolidifier;
    public static Block venusFlyTrap;
    public static Block totemWoods;
    public static Block totemDraining;
    public static Block totemTable;
    public static Block totemIntelligence;
    public static Block totemSocket;
    public static Block chlorophyll;
    public static Block totemSapling;

    public static void init()
    {

        chlorophyllSolidifier = new BlockChlorophyllSolidifier(BlockIds.CHLOROPHYLL_SOLIDIFIER);
        //venusFlyTrap = new BlockVenusFlyTrap(BlockIds.VENUS_FLY_TRAP);
        totemWoods = new BlockTotemWoods(BlockIds.TOTEM_WOODS);
        totemDraining = new BlockTotemDraining(BlockIds.TOTEM_DRAINING);
        totemTable = new BlockTotemTable(BlockIds.TOTEM_TABLE);
        totemIntelligence = new BlockTotemIntelligence(BlockIds.TOTEM_INTELLIGENCE);
        totemSocket = new BlockTotemSocket(BlockIds.TOTEM_SOCKET);
        chlorophyll = new BlockChlorophyll(BlockIds.CHLOROPHYLL);
        //totemSapling = new BlockTotemSapling(BlockIds.TOTEM_SAPLING);

        GameRegistry.registerBlock(chlorophyllSolidifier, Strings.CHLOROPHYLL_SOLIDIFIER_NAME);
        //GameRegistry.registerBlock(venusFlyTrap, Strings.VENUS_FLY_TRAP_NAME);
        GameRegistry.registerBlock(totemWoods, Strings.TOTEM_WOODS_NAME);
        GameRegistry.registerBlock(totemDraining, Strings.TOTEM_DRAINING_NAME);
        GameRegistry.registerBlock(totemTable, Strings.TOTEM_TABLE_NAME);
        GameRegistry.registerBlock(totemIntelligence, Strings.TOTEM_INTELLIGENCE_NAME);
        GameRegistry.registerBlock(totemSocket, Strings.TOTEM_SOCKET_NAME);
        GameRegistry.registerBlock(chlorophyll, Strings.FLUID_CHLOROPHYLL_NAME);
        //GameRegistry.registerBlock(totemSapling, Strings.TOTEM_SAPLING_NAME);


    }

}
