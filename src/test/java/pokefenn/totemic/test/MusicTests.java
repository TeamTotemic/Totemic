package pokefenn.totemic.test;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertPosException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.totem.entity.StateTotemEffect;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
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
        TotemicAPI.get().music().playMusic(h.getLevel(), h.absolutePos(playPos), null, ModContent.flute);
        assertTotemMusic(h, totem, ModContent.flute.getBaseOutput());
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
}
