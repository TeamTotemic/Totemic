package pokefenn.totemic.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.tile.totem.TileTotemBase;

@ObjectHolder(TotemicAPI.MOD_ID)
public final class ModTileEntities {
    public static final BlockEntityType<TileTotemBase> totem_base = null;

    @SubscribeEvent
    public static void init(RegistryEvent.Register<BlockEntityType<?>> event) {
        event.getRegistry().registerAll(BlockEntityType.Builder.of(TileTotemBase::new, ModBlocks.getTotemBases().values().toArray(new Block[0])).build(null)
                .setRegistryName("totem_base"));
    }
}
