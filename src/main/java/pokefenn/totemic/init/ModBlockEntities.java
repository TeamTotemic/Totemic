package pokefenn.totemic.init;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.neoforge.registries.DeferredRegister;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.music.entity.WindChimeBlockEntity;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.block.totem.entity.TotemPoleBlockEntity;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TotemicAPI.MOD_ID);

    public static final Supplier<BlockEntityType<TotemBaseBlockEntity>> totem_base = REGISTER.register("totem_base", () -> Builder.of(TotemBaseBlockEntity::new, ModBlocks.totem_base.get()).build(null));
    public static final Supplier<BlockEntityType<TotemPoleBlockEntity>> totem_pole = REGISTER.register("totem_pole", () -> Builder.of(TotemPoleBlockEntity::new, ModBlocks.totem_pole.get()).build(null));
    public static final Supplier<BlockEntityType<WindChimeBlockEntity>> wind_chime = REGISTER.register("wind_chime", () -> Builder.of(WindChimeBlockEntity::new, ModBlocks.wind_chime.get()).build(null));
}
