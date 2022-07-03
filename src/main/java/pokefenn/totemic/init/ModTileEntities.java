package pokefenn.totemic.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.tile.totem.TileTotemBase;

public final class ModTileEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, TotemicAPI.MOD_ID);

    public static final RegistryObject<BlockEntityType<TileTotemBase>> totem_base = REGISTER.register("totem_base", () -> BlockEntityType.Builder.of(TileTotemBase::new, ModBlocks.getTotemBases().values().toArray(Block[]::new)).build(null));
}
