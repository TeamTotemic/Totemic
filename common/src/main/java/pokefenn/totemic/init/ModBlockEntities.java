package pokefenn.totemic.init;

import java.util.Set;
import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import pokefenn.totemic.PlatformRegistryHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.music.entity.WindChimeBlockEntity;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.block.totem.entity.TotemPoleBlockEntity;

public final class ModBlockEntities {
    public static final PlatformRegistryHelper<BlockEntityType<?>> REGISTER = Totemic.platform().createRegistryHelper(Registries.BLOCK_ENTITY_TYPE);

    public static final Supplier<BlockEntityType<TotemBaseBlockEntity>> totem_base = REGISTER.register(
            "totem_base",
            () -> new BlockEntityType<>(TotemBaseBlockEntity::new, Set.of(ModBlocks.totem_base.get()))
    );
    public static final Supplier<BlockEntityType<TotemPoleBlockEntity>> totem_pole = REGISTER.register(
            "totem_pole",
            () -> new BlockEntityType<>(TotemPoleBlockEntity::new, Set.of(ModBlocks.totem_pole.get()))
    );
    public static final Supplier<BlockEntityType<WindChimeBlockEntity>> wind_chime = REGISTER.register(
            "wind_chime",
            () -> new BlockEntityType<>(WindChimeBlockEntity::new, Set.of(ModBlocks.wind_chime.get()))
    );
}
