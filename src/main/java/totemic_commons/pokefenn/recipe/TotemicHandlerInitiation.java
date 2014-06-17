package totemic_commons.pokefenn.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.TotemicAPI;
import totemic_commons.pokefenn.api.recipe.CeremonyPotionRegistry;
import totemic_commons.pokefenn.api.recipe.CeremonyRegistry;
import totemic_commons.pokefenn.api.recipe.TotemRegistry;
import totemic_commons.pokefenn.item.ItemTotems;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.totem.*;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TotemicHandlerInitiation
{
    public static CeremonyRegistry ghostDance;
    public static CeremonyRegistry elixer;
    public static CeremonyRegistry rainDance;
    public static CeremonyRegistry drought;
    public static CeremonyRegistry flute;

    public static TotemRegistry horseTotem;
    public static TotemRegistry squidTotem;
    public static TotemRegistry blazeTotem;
    public static TotemRegistry ocelotTotem;
    public static TotemRegistry batTotem;
    public static TotemRegistry spiderTotem;
    public static TotemRegistry cowTotem;

    public static void init()
    {
        totemRegistry();
        ceremonyHandler();
        ceremonyPotionHandler();
    }

    public static void ceremonyHandler()
    {
        //CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry(false, MusicEnum.FLUTE, MusicEnum.DRUM, MusicEnum.DRUM, MusicEnum.FLUTE, 1, new CeremonyFlowingTime(), false, 20 * 30, MusicEnum.FLUTE, null, 100, 30 * 20, 0));
        //TODO
        //elixer = TotemicAPI.addCeremony("Dance of Divine Elixirs", false, null, new MusicEnum[]{MusicEnum.DRUM, MusicEnum.FLUTE, MusicEnum.DRUM, MusicEnum.FLUTE}, 2, new CeremonyPotion(), false, 20 * 30, 150, 30 * 20, 2);
        //flute = TotemicAPI.addCeremony("Ceremony of Flute Imbuation", false, null, new MusicEnum[]{MusicEnum.FLUTE, MusicEnum.FLUTE, MusicEnum.FLUTE, MusicEnum.FLUTE}, 3, new CeremonyFluteInfusion(), true, 20 * 30, 110, 40 * 20, 0);
        //rainDance = TotemicAPI.addCeremony("Rain Dance", false, null, new MusicEnum[]{MusicEnum.DRUM, MusicEnum.DRUM, MusicEnum.FLUTE, MusicEnum.FLUTE}, 4, new CeremonyRain(), true, 20 * 30, 110, 40 * 20, 0);
        //drought = TotemicAPI.addCeremony("Drought Ceremony", false, null, new MusicEnum[]{MusicEnum.FLUTE, MusicEnum.FLUTE, MusicEnum.DRUM, MusicEnum.DRUM}, 5, new CeremonyRainRemoval(), true, 20 * 30, 110, 40 * 20, 0);
        //CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry("Ghost Dance", false, null, null, null, null, 6, new CeremonyGhostDance(), true, 20 * 30, null, 130, 40 * 20, 0));
        //CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry(false, MusicEnum.WIND_CHIME, MusicEnum.FLUTE, MusicEnum.DRUM, MusicEnum.DRUM, 4, new CeremonyTotemAwakening(), true, 20 * 30, MusicEnum.DRUM, null, 0, 30 * 20, 0))
    }

    public static void totemRegistry()
    {
        horseTotem = TotemicAPI.addTotem(0, new ItemStack(ModItems.totems, 1, ItemTotems.horse), 4, 4, new TotemEffectHorse(), 1, ItemTotems.TOTEM_NAMES[ItemTotems.horse]);
        squidTotem = TotemicAPI.addTotem(1, new ItemStack(ModItems.totems, 1, ItemTotems.squid), 4, 4, new TotemEffectSquid(), 1, ItemTotems.TOTEM_NAMES[ItemTotems.squid]);
        blazeTotem = TotemicAPI.addTotem(2, new ItemStack(ModItems.totems, 1, ItemTotems.blaze), 4, 4, new TotemEffectBlaze(), 2, ItemTotems.TOTEM_NAMES[ItemTotems.blaze]);
        ocelotTotem = TotemicAPI.addTotem(3, new ItemStack(ModItems.totems, 1, ItemTotems.ocelot), 4, 4, new TotemEffectOcelot(), 2, ItemTotems.TOTEM_NAMES[ItemTotems.ocelot]);
        batTotem = TotemicAPI.addTotem(4, new ItemStack(ModItems.totems, 1, ItemTotems.bat), 4, 8, new TotemEffectBat(), 2, ItemTotems.TOTEM_NAMES[ItemTotems.bat]);
        spiderTotem = TotemicAPI.addTotem(5, new ItemStack(ModItems.totems, 1, ItemTotems.spider), 4, 4, new TotemEffectSpider(), 2, ItemTotems.TOTEM_NAMES[ItemTotems.spider]);
        cowTotem = TotemicAPI.addTotem(6, new ItemStack(ModItems.totems, 1, ItemTotems.cow), 4, 4, new TotemEffectCow(), 1, ItemTotems.TOTEM_NAMES[ItemTotems.cow]);
    }

    public static void ceremonyPotionHandler()
    {
        CeremonyPotionRegistry.ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.string, 8, 0), ModPotions.spiderPotion, 0, 60 * 20));
        CeremonyPotionRegistry.ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.ghast_tear, 8, 0), Potion.regeneration, 0, 40 * 20));
        CeremonyPotionRegistry.ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.blaze_powder, 4, 0), Potion.fireResistance, 0, 60 * 20));
        CeremonyPotionRegistry.ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.golden_carrot, 8, 0), Potion.invisibility, 0, 60 * 20));
        CeremonyPotionRegistry.ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.milk_bucket, 1, 0), ModPotions.antidotePotion, 0, 60 * 20));
        //CeremonyPotionRegistry.ceremonyRegistry.add(new CeremonyPotionRegistry(new ItemStack(Items.spider_eye, 8, 0), Potion.wither, 0, 30 * 20));
    }
}
