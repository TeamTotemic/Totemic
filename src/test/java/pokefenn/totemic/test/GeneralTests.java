package pokefenn.totemic.test;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import pokefenn.totemic.api.TotemicAPI;

@GameTestHolder(TotemicAPI.MOD_ID)
@PrefixGameTestTemplate(false)
public final class GeneralTests {
    @GameTest(template = "test1")
    public static void testTest(GameTestHelper helper) {
        helper.succeed();
    }
}
