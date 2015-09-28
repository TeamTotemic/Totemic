package totemic_commons.pokefenn.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.TotemicAPI;
import totemic_commons.pokefenn.api.ceremony.*;
import totemic_commons.pokefenn.api.music.MusicHandler;
import totemic_commons.pokefenn.api.totem.TotemRegistry;
import totemic_commons.pokefenn.ceremony.*;
import totemic_commons.pokefenn.totem.*;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class HandlerInitiation
{
    public static CeremonyRegistry ghostDance;
    public static CeremonyRegistry rainDance;
    public static CeremonyRegistry drought;
    public static CeremonyRegistry fluteCeremony;
    public static CeremonyRegistry zaphkielWaltz;
    public static CeremonyRegistry warDance;
    public static CeremonyRegistry buffaloDance;

    public static TotemRegistry horseTotem;
    public static TotemRegistry squidTotem;
    public static TotemRegistry blazeTotem;
    public static TotemRegistry ocelotTotem;
    public static TotemRegistry batTotem;
    public static TotemRegistry spiderTotem;
    public static TotemRegistry cowTotem;

    public static MusicHandler flute;
    public static MusicHandler drum;
    public static MusicHandler windChime;
    public static MusicHandler jingleDress;
    public static MusicHandler rattle;

    public static void init()
    {
        totemRegistry();
        instruments();
        ceremonyHandler();
    }

    static void ceremonyHandler()
    {
        fluteCeremony = TotemicAPI.addCeremony("totemic.ceremony.flute", 2, new CeremonyEffect(new CeremonyFluteInfusion(),
                new MusicHandler[]{flute, flute, flute, flute}), new CeremonyActivation(TimeStateEnum.INSTANT, 140, CeremonyTimeEnum.MEDIUM));
        rainDance = TotemicAPI.addCeremony("totemic.ceremony.rainDance", 3, new CeremonyEffect(new CeremonyRain(),
                new MusicHandler[]{rattle, rattle, flute, flute}), new CeremonyActivation(TimeStateEnum.INSTANT, 130, CeremonyTimeEnum.MEDIUM));
        drought = TotemicAPI.addCeremony("totemic.ceremony.drought", 4, new CeremonyEffect(new CeremonyRainRemoval(),
                new MusicHandler[]{flute, flute, rattle, rattle}), new CeremonyActivation(TimeStateEnum.INSTANT, 130, CeremonyTimeEnum.MEDIUM));
        ghostDance = TotemicAPI.addCeremony("totemic.ceremony.ghostDance", 5, new CeremonyEffect(new CeremonyGhostDance(),
                new MusicHandler[]{rattle, rattle, rattle, rattle}), new CeremonyActivation(TimeStateEnum.INSTANT, 160, CeremonyTimeEnum.SHORT_MEDIUM));
        zaphkielWaltz = TotemicAPI.addCeremony("totemic.ceremony.zaphkielWaltz", 6, new CeremonyEffect(new CeremonyZaphkielWaltz(),
                new MusicHandler[]{flute, drum, drum, flute}), new CeremonyActivation(TimeStateEnum.ENDING_EFFECT, CeremonyTimeEnum.SHORT_MEDIUM, 110, CeremonyTimeEnum.MEDIUM, 6));
        warDance = TotemicAPI.addCeremony("totemic.ceremony.warDance", 7, new CeremonyEffect(new CeremonyWarDance(),
                new MusicHandler[]{drum, drum, drum, drum}), new CeremonyActivation(TimeStateEnum.INSTANT, 120, CeremonyTimeEnum.MEDIUM));
        buffaloDance = TotemicAPI.addCeremony("totemic.ceremony.buffaloDance", 8, new CeremonyEffect(new CeremonyBuffaloDance(),
                new MusicHandler[]{jingleDress, flute, drum, windChime}), new CeremonyActivation(TimeStateEnum.INSTANT, 150, CeremonyTimeEnum.MEDIUM));
    }

    static void totemRegistry()
    {
        horseTotem = TotemicAPI.addTotem(1, 4, 4, new TotemEffectHorse(), 1, "totemic.totem.horse");
        squidTotem = TotemicAPI.addTotem(2, 4, 4, new TotemEffectSquid(), 1, "totemic.totem.squid");
        blazeTotem = TotemicAPI.addTotem(3, 4, 4, new TotemEffectBlaze(), 2, "totemic.totem.blaze");
        ocelotTotem = TotemicAPI.addTotem(4, 4, 4, new TotemEffectOcelot(), 2, "totemic.totem.ocelot");
        batTotem = TotemicAPI.addTotem(5, 8, 8, new TotemEffectBat(), 2, "totemic.totem.bat");
        spiderTotem = TotemicAPI.addTotem(6, 4, 4, new TotemEffectSpider(), 2, "totemic.totem.spider");
        cowTotem = TotemicAPI.addTotem(7, 4, 4, new TotemEffectCow(), 1, "totemic.totem.cow");
    }

    static void instruments()
    {
        //TODO: Correct values
    	flute = TotemicAPI.addInstrument(0, 5, 5, 150);
    	drum = TotemicAPI.addInstrument(1, 7, 5, 150);
    	windChime = TotemicAPI.addInstrument(2, 8, 5, 150);
    	jingleDress = TotemicAPI.addInstrument(3, 6, 5, 150);
    	rattle = TotemicAPI.addInstrument(4, 6, 5, 150);
    }

    public static void instrumentItems()
    {
        flute.setItems(new ItemStack(ModItems.flute));
        drum.setItems(new ItemStack(ModBlocks.drum));
        windChime.setItems(new ItemStack(ModBlocks.windChime));
        jingleDress.setItems(new ItemStack(ModItems.jingleDress));
        rattle.setItems(new ItemStack(ModItems.ceremonialRattle));
    }
}
