package pokefenn.totemic.init;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.tile.totem.TileTotemBase;

@ObjectHolder(Totemic.MOD_ID)
public final class ModTileEntities {
    public static final TileEntityType<TileTotemBase> totem_base = null;

    @SubscribeEvent
    public static void init(RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().registerAll(TileEntityType.Builder.create(TileTotemBase::new, ModBlocks.getTotemBases().values().toArray(new Block[0])).build(null)
                .setRegistryName("totem_base"));
    }
}
