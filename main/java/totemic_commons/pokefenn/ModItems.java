package totemic_commons.pokefenn;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.fluid.ItemBottleChlorophyll;
import totemic_commons.pokefenn.fluid.ItemBucketChlorophyll;
import totemic_commons.pokefenn.item.*;
import totemic_commons.pokefenn.item.plant.ItemMoonglowSeeds;
import totemic_commons.pokefenn.item.plant.ItemWaterLotusSeed;
import totemic_commons.pokefenn.lib.Strings;

public final class ModItems
{

    public static Item totemWhittlingKnife;
    public static Item totemicStaff;
    public static Item chlorophyllCrystal;
    public static Item bucketChlorophyll;
    public static Item venusFlyTrapSeed;
    public static Item subItems;
    public static Item bottleChlorophyll;
    public static Item totems;
    public static Item totemBeads;
    public static Item blazingChlorophyllCrystal;
    public static Item infusedTotemicStaff;
    public static Item totempedia;
    public static Item potion;
    public static Item moonglowSeeds;
    public static Item bloodwart;
    public static Item lotusSeed;

    public static void init()
    {

        totemWhittlingKnife = new ItemTotemWhittlingKnife();
        totemicStaff = new ItemTotemicStaff();
        chlorophyllCrystal = new ItemVerdantCrystal();
        bucketChlorophyll = new ItemBucketChlorophyll();
        totems = new ItemTotems();
        subItems = new ItemSubItems();
        bottleChlorophyll = new ItemBottleChlorophyll();
        blazingChlorophyllCrystal = new ItemBlazingVerdantCrystal();
        infusedTotemicStaff = new ItemInfusedTotemicStaff();
        //totempedia = new ItemTotempedia();
        potion = new ItemPotions();
        moonglowSeeds = new ItemMoonglowSeeds(ModBlocks.moonglow, Blocks.dirt);
        bloodwart = new ItemBloodWart(ModBlocks.bloodwart, Blocks.soul_sand);
        lotusSeed = new ItemWaterLotusSeed();

        //Registry for other items
        GameRegistry.registerItem(totemWhittlingKnife, totemWhittlingKnife.getUnlocalizedName());
        GameRegistry.registerItem(totemicStaff, totemicStaff.getUnlocalizedName());
        GameRegistry.registerItem(chlorophyllCrystal, chlorophyllCrystal.getUnlocalizedName());
        GameRegistry.registerItem(bucketChlorophyll, bucketChlorophyll.getUnlocalizedName());
        //GameRegistry.registerItem(venusFlyTrapSeed, venusFlyTrapSeed.getUnlocalizedName());
        GameRegistry.registerItem(subItems, Strings.SUB_ITEMS_NAME);
        GameRegistry.registerItem(bottleChlorophyll, bottleChlorophyll.getUnlocalizedName());
        GameRegistry.registerItem(blazingChlorophyllCrystal, blazingChlorophyllCrystal.getUnlocalizedName());
        GameRegistry.registerItem(infusedTotemicStaff, infusedTotemicStaff.getUnlocalizedName());
        //GameRegistry.registerItem(totempedia, totempedia.getUnlocalizedName());
        GameRegistry.registerItem(totems, Strings.TOTEMS_NAME);
        GameRegistry.registerItem(potion, Strings.TOTEMIC_POTION_NAME);
        GameRegistry.registerItem(moonglowSeeds, Strings.MOONGLOW_SEEDS_NAME);
        GameRegistry.registerItem(bloodwart, Strings.BLOODWART_NAME);
        GameRegistry.registerItem(lotusSeed, Strings.LOTUS_SEED_NAME);

        //totemWhittlingKnife.setContainerItem(totemWhittlingKnife);
        //bucketChlorophyll.setContainerItem(bucketChlorophyll);

        if (!ConfigurationSettings.DISABLE_TOTEM_BEADS)
        {
            //totemBeads = new ItemTotemBeads(ItemIds.TOTEM_BEADS);
            //GameRegistry.registerItem(totemBeads, totemBeads.getUnlocalizedName());
        }


    }


}
