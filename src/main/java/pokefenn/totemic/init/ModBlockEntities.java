package pokefenn.totemic.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.music.entity.WindChimeBlockEntity;
import pokefenn.totemic.block.totem.entity.TileTotemBase;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TotemicAPI.MOD_ID);

    public static final RegistryObject<BlockEntityType<TileTotemBase>> totem_base = REGISTER.register("totem_base", () ->
            Builder.of(TileTotemBase::new, ModBlocks.getTotemBases().values().stream()
                    .map(RegistryObject::get)
                    .toArray(Block[]::new))
            .build(null));
    public static final RegistryObject<BlockEntityType<WindChimeBlockEntity>> wind_chime = REGISTER.register("wind_chime", () -> Builder.of(WindChimeBlockEntity::new, ModBlocks.wind_chime.get()).build(null));
}
