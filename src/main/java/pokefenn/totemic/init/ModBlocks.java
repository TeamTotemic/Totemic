package pokefenn.totemic.init;

import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.totem.BlockTotemBase;

@ObjectHolder(Totemic.MOD_ID)
public final class ModBlocks {
    public static final BlockTotemBase totem_base = null;

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
            new BlockTotemBase(Properties.create(Material.WOOD).hardnessAndResistance(2, 5).sound(SoundType.WOOD)).setRegistryName("totem_base")
        );
    }
}
