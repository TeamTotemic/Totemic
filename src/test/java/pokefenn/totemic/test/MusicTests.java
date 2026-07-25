package pokefenn.totemic.test;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertPosException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.music.entity.WindChimeBlockEntity;
import pokefenn.totemic.block.totem.entity.StateTotemEffect;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;

@GameTestHolder(TotemicAPI.MOD_ID)
@PrefixGameTestTemplate(false)
public final class MusicTests {
    @GameTest(batch = "totemic.music", template = "music/play_music")
    public static void testPlayMusic(GameTestHelper h) {
        final BlockPos totemPos = new BlockPos(0, 1, 0);
        final BlockPos playPos = new BlockPos(1, 1, 0);
        var totem = (TotemBaseBlockEntity) h.getBlockEntity(totemPos);

        assertTotemMusic(h, totem, 0);
        TotemicAPI.get().music().playMusic(h.getLevel(), h.absolutePos(playPos), null, ModContent.flute.get());
        assertTotemMusic(h, totem, ModContent.flute.get().getBaseOutput());
        h.succeed();
    }

    private static void assertTotemMusic(GameTestHelper h, TotemBaseBlockEntity totem, int expectedMusic) {
        if(totem.getTotemState() instanceof StateTotemEffect state) {
            if(state.getTotemEffectMusic() != expectedMusic)
                throw new GameTestAssertPosException("Expected music amount of " + expectedMusic + ", actual amount: " + state.getTotemEffectMusic(), totem.getBlockPos(), h.relativePos(totem.getBlockPos()), h.getTick());
        }
        else
            throw new GameTestAssertPosException("Expected StateTotemEffect", totem.getBlockPos(), h.relativePos(totem.getBlockPos()), h.getTick());
    }

    @GameTest(batch = "totemic.music", template = "music/wind_chime_congestion")
    public static void testWindChimeCongestion(GameTestHelper h) {
        h.startSequence()
        .thenExecute(() -> {
            h.setBlock(8, 2, 8, ModBlocks.wind_chime.get());
            h.setBlock(9, 2, 8, ModBlocks.wind_chime.get());
            h.setBlock(10, 2, 8, ModBlocks.wind_chime.get());
        })
        .thenExecuteAfter(1, () -> {
            assertChimeCongestionStatus(h, new BlockPos(8, 2, 8), false);
            assertChimeCongestionStatus(h, new BlockPos(9, 2, 8), false);
            assertChimeCongestionStatus(h, new BlockPos(10, 2, 8), false);

            h.setBlock(11, 2, 8, ModBlocks.wind_chime.get());
        })
        .thenExecuteAfter(1, () -> {
            assertChimeCongestionStatus(h, new BlockPos(8, 2, 8), false);
            assertChimeCongestionStatus(h, new BlockPos(9, 2, 8), false);
            assertChimeCongestionStatus(h, new BlockPos(10, 2, 8), false);
            assertChimeCongestionStatus(h, new BlockPos(11, 2, 8), true);

            h.setBlock(8, 2, 8, Blocks.AIR);
        })
        .thenExecuteAfter(1, () -> {
            assertChimeCongestionStatus(h, new BlockPos(9, 2, 8), false);
            assertChimeCongestionStatus(h, new BlockPos(10, 2, 8), false);
            assertChimeCongestionStatus(h, new BlockPos(11, 2, 8), false);

            h.setBlock(17, 2, 8, ModBlocks.wind_chime.get());
            h.setBlock(18, 2, 8, ModBlocks.wind_chime.get());
        })
        .thenExecuteAfter(1, () -> {
            assertChimeCongestionStatus(h, new BlockPos(9, 2, 8), false);
            assertChimeCongestionStatus(h, new BlockPos(10, 2, 8), false);
            assertChimeCongestionStatus(h, new BlockPos(11, 2, 8), false);
            assertChimeCongestionStatus(h, new BlockPos(17, 2, 8), true);
            assertChimeCongestionStatus(h, new BlockPos(18, 2, 8), false); // far enough from (9, 2, 8) that it shouldn't get congested

            h.setBlock(9, 2, 8, Blocks.AIR);
        })
        .thenExecuteAfter(1, () -> {
            assertChimeCongestionStatus(h, new BlockPos(10, 2, 8), false);
            assertChimeCongestionStatus(h, new BlockPos(11, 2, 8), false);
            assertChimeCongestionStatus(h, new BlockPos(17, 2, 8), true);
            assertChimeCongestionStatus(h, new BlockPos(18, 2, 8), false);

            h.setBlock(10, 2, 8, Blocks.AIR);
        })
        .thenExecuteAfter(1, () -> {
            assertChimeCongestionStatus(h, new BlockPos(11, 2, 8), false);
            assertChimeCongestionStatus(h, new BlockPos(17, 2, 8), false);
            assertChimeCongestionStatus(h, new BlockPos(18, 2, 8), false);
        })
        .thenSucceed();
    }

    private static void assertChimeCongestionStatus(GameTestHelper h, BlockPos pos, boolean expectedCongestion) {
        h.assertBlockEntityData(pos, (WindChimeBlockEntity chime) -> chime.isCongested() == expectedCongestion,
                () -> "Expected Wind Chime to be " + (expectedCongestion ? "congested" : "not congested"));
    }
}
