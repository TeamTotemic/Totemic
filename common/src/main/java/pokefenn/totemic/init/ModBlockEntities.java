package pokefenn.totemic.init;

import java.util.Set;
import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import pokefenn.totemic.PlatformRegistryHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.music.entity.WindChimeBlockEntity;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.block.totem.entity.TotemPoleBlockEntity;

public final class ModBlockEntities {
    public static final PlatformRegistryHelper<BlockEntityType<?>> REGISTER = Totemic.platform().createRegistryHelper(Registries.BLOCK_ENTITY_TYPE);

    public static final Supplier<BlockEntityType<TotemBaseBlockEntity>> totem_base = register("totem_base", TotemBaseBlockEntity::new, ModBlocks.totem_base.get());
    public static final Supplier<BlockEntityType<TotemPoleBlockEntity>> totem_pole = register("totem_pole", TotemPoleBlockEntity::new, ModBlocks.totem_pole.get());
    public static final Supplier<BlockEntityType<WindChimeBlockEntity>> wind_chime = register("wind_chime", WindChimeBlockEntity::new, ModBlocks.wind_chime.get());

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<? extends T> factory, Block... validBlocks) {
        return REGISTER.register(name, () -> new BlockEntityType<T>(factory, Set.of(validBlocks)));
    }
}
