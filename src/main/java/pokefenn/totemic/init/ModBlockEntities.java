package pokefenn.totemic.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.music.entity.WindChimeBlockEntity;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.block.totem.entity.TotemPoleBlockEntity;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TotemicAPI.MOD_ID);

    public static final RegistryObject<BlockEntityType<TotemBaseBlockEntity>> totem_base = REGISTER.register("totem_base", () -> Builder.of(TotemBaseBlockEntity::new, ModBlocks.totem_base.get()).build(null));
    public static final RegistryObject<BlockEntityType<TotemPoleBlockEntity>> totem_pole = REGISTER.register("totem_pole", () -> Builder.of(TotemPoleBlockEntity::new, ModBlocks.totem_pole.get()).build(null));
    public static final RegistryObject<BlockEntityType<WindChimeBlockEntity>> wind_chime = REGISTER.register("wind_chime", () -> Builder.of(WindChimeBlockEntity::new, ModBlocks.wind_chime.get()).build(null));
}
