package pokefenn.totemic.api;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tags.BlockTags;

public final class TotemWoodType { // TODO: Allow registering new wood types, or use vanilla's WoodType instead
    public static final TotemWoodType OAK = new TotemWoodType("oak", MaterialColor.WOOD, MaterialColor.PODZOL);
    public static final TotemWoodType SPRUCE = new TotemWoodType("spruce", MaterialColor.PODZOL, MaterialColor.COLOR_BROWN);
    public static final TotemWoodType BIRCH = new TotemWoodType("birch", MaterialColor.SAND, MaterialColor.QUARTZ);
    public static final TotemWoodType JUNGLE = new TotemWoodType("jungle", MaterialColor.DIRT, MaterialColor.PODZOL);
    public static final TotemWoodType ACACIA = new TotemWoodType("acacia", MaterialColor.COLOR_ORANGE, MaterialColor.STONE);
    public static final TotemWoodType DARK_OAK = new TotemWoodType("dark_oak", MaterialColor.COLOR_BROWN, MaterialColor.COLOR_BROWN);
    public static final TotemWoodType CEDAR = new TotemWoodType("cedar", MaterialColor.COLOR_PINK, MaterialColor.COLOR_ORANGE);

    private static final List<TotemWoodType> woodTypes = ImmutableList.of(OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK, CEDAR);

    private final String name;
    private final MaterialColor woodColor;
    private final MaterialColor barkColor;

    @Nullable
    public static TotemWoodType fromLog(BlockState state) {
        Block block = state.getBlock();
        if(BlockTags.OAK_LOGS.contains(block))
            return OAK;
        else if(BlockTags.SPRUCE_LOGS.contains(block))
            return SPRUCE;
        else if(BlockTags.BIRCH_LOGS.contains(block))
            return BIRCH;
        else if(BlockTags.JUNGLE_LOGS.contains(block))
            return JUNGLE;
        else if(BlockTags.ACACIA_LOGS.contains(block))
            return ACACIA;
        else if(BlockTags.DARK_OAK_LOGS.contains(block))
            return DARK_OAK;
        /*else if(ModBlockTags.CEDAR_LOGS.contains(block)) //TODO
            return CEDAR;*/
        else
            return null;
    }

    public static List<TotemWoodType> getWoodTypes() {
        return woodTypes;
    }

    public TotemWoodType(String name, MaterialColor woodColor, MaterialColor barkColor) {
        this.name = name;
        this.woodColor = woodColor;
        this.barkColor = barkColor;
    }

    public String getName() {
        return name;
    }

    public MaterialColor getWoodColor() {
        return woodColor;
    }

    public MaterialColor getBarkColor() {
        return barkColor;
    }
}
