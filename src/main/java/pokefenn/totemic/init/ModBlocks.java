package pokefenn.totemic.init;

import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.totem.BlockTotemBase;

@ObjectHolder(Totemic.MOD_ID)
public final class ModBlocks {
    public static final BlockTotemBase oak_totem_base = null;
    public static final BlockTotemBase spruce_totem_base = null;
    public static final BlockTotemBase birch_totem_base = null;
    public static final BlockTotemBase jungle_totem_base = null;
    public static final BlockTotemBase acacia_totem_base = null;
    public static final BlockTotemBase dark_oak_totem_base = null;
    public static final BlockTotemBase cedar_totem_base = null;

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
            new BlockTotemBase(Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2, 5).sound(SoundType.WOOD)).setRegistryName("oak_totem_base"),
            new BlockTotemBase(Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2, 5).sound(SoundType.WOOD)).setRegistryName("spruce_totem_base"),
            new BlockTotemBase(Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2, 5).sound(SoundType.WOOD)).setRegistryName("birch_totem_base"),
            new BlockTotemBase(Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(2, 5).sound(SoundType.WOOD)).setRegistryName("jungle_totem_base"),
            new BlockTotemBase(Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2, 5).sound(SoundType.WOOD)).setRegistryName("acacia_totem_base"),
            new BlockTotemBase(Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2, 5).sound(SoundType.WOOD)).setRegistryName("dark_oak_totem_base"),
            new BlockTotemBase(Properties.create(Material.WOOD, MaterialColor.PINK).hardnessAndResistance(2, 5).sound(SoundType.WOOD)).setRegistryName("cedar_totem_base")
        );
    }
}
