package pokefenn.totemic.api;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;

public final class TotemWoodType { // TODO: Allow registering new wood types
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
        if(state.is(BlockTags.OAK_LOGS))
            return OAK;
        else if(state.is(BlockTags.SPRUCE_LOGS))
            return SPRUCE;
        else if(state.is(BlockTags.BIRCH_LOGS))
            return BIRCH;
        else if(state.is(BlockTags.JUNGLE_LOGS))
            return JUNGLE;
        else if(state.is(BlockTags.ACACIA_LOGS))
            return ACACIA;
        else if(state.is(BlockTags.DARK_OAK_LOGS))
            return DARK_OAK;
        /*else if(state.is(ModBlockTags.CEDAR_LOGS)) //TODO
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
