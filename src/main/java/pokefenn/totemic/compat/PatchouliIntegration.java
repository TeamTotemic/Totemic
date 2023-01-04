package pokefenn.totemic.compat;

import java.util.function.Predicate;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicBlockTags;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import vazkii.patchouli.api.PatchouliAPI;

public final class PatchouliIntegration {
    public static void init() {
        var api = PatchouliAPI.get();
        if(api.isStub()) //shouldn't happen as Patchouli is declared as required dependency in mods.toml
            throw new RuntimeException("Totemic requires Patcholi to be installed");

        Predicate<BlockState> isTotemBase = state -> state.is(TotemicBlockTags.TOTEM_BASES);
        Predicate<BlockState> isTotemPole = state -> state.is(TotemicBlockTags.TOTEM_POLES);

        //Totem Pole multiblock which can match any type of base/pole but displays concrete examples
        var totemPoleMultiblock = api.makeMultiblock(new String[][] {
            {"E"},
            {"D"},
            {"C"},
            {"B"},
            {"A"},
            {"0"}
        },
                '0', api.predicateMatcher(ModBlocks.getTotemBase(TotemWoodType.OAK), isTotemBase),
                'A', api.predicateMatcher(ModBlocks.getTotemPole(TotemWoodType.OAK, ModContent.buffalo), isTotemPole),
                'B', api.predicateMatcher(ModBlocks.getTotemPole(TotemWoodType.OAK, ModContent.spider), isTotemPole),
                'C', api.predicateMatcher(ModBlocks.getTotemPole(TotemWoodType.OAK, ModContent.ocelot), isTotemPole),
                'D', api.predicateMatcher(ModBlocks.getTotemPole(TotemWoodType.OAK, ModContent.blaze), isTotemPole),
                'E', api.predicateMatcher(ModBlocks.getTotemPole(TotemWoodType.OAK, ModContent.bat), isTotemPole));
        api.registerMultiblock(new ResourceLocation(TotemicAPI.MOD_ID, "example_totem_pole"), totemPoleMultiblock);
    }
}
