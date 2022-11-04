package pokefenn.totemic.api;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;

/**
 * Represents a wood type that Totem Poles can be made out of.
 */
public final class TotemWoodType { // TODO: Allow registering new wood types
    public static final TotemWoodType OAK = new TotemWoodType("oak", MaterialColor.WOOD, MaterialColor.PODZOL, "minecraft");
    public static final TotemWoodType SPRUCE = new TotemWoodType("spruce", MaterialColor.PODZOL, MaterialColor.COLOR_BROWN, "minecraft");
    public static final TotemWoodType BIRCH = new TotemWoodType("birch", MaterialColor.SAND, MaterialColor.QUARTZ, "minecraft");
    public static final TotemWoodType JUNGLE = new TotemWoodType("jungle", MaterialColor.DIRT, MaterialColor.PODZOL, "minecraft");
    public static final TotemWoodType ACACIA = new TotemWoodType("acacia", MaterialColor.COLOR_ORANGE, MaterialColor.STONE, "minecraft");
    public static final TotemWoodType DARK_OAK = new TotemWoodType("dark_oak", MaterialColor.COLOR_BROWN, MaterialColor.COLOR_BROWN, "minecraft");
    public static final TotemWoodType MANGROVE = new TotemWoodType("mangrove", MaterialColor.COLOR_RED, MaterialColor.PODZOL, "minecraft");
    public static final TotemWoodType CEDAR = new TotemWoodType("cedar", MaterialColor.COLOR_PINK, MaterialColor.COLOR_ORANGE, "totemic");

    private static final List<TotemWoodType> woodTypes = List.of(OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK, MANGROVE, CEDAR);

    private final String name;
    private final MaterialColor woodColor;
    private final MaterialColor barkColor;

    private final String woodTexture;
    private final String barkTexture;
    private final String topTexture;
    private final String particleTexture;

    /**
     * Returns the TotemWoodType represented by the given BlockState, or {@code null} if the state is not a recognized log block.
     */
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
        else if(state.is(BlockTags.MANGROVE_LOGS))
            return MANGROVE;
        else if(state.is(TotemicBlockTags.CEDAR_LOGS))
            return CEDAR;
        else
            return null;
    }

    /**
     * Returns a list of all TotemWoodTypes.
     */
    public static List<TotemWoodType> getWoodTypes() {
        return woodTypes;
    }

    private TotemWoodType(String name, MaterialColor woodColor, MaterialColor barkColor, String textureKey) {
        this.name = name;
        this.woodColor = woodColor;
        this.barkColor = barkColor;

        this.woodTexture = textureKey + ":block/stripped_" + name + "_log";
        this.barkTexture = textureKey + ":block/" + name + "_log";
        this.topTexture = textureKey + ":block/stripped_" + name + "_log_top";
        this.particleTexture = textureKey + ":block/stripped_" + name + "_log";
    }

    /**
     * Returns the name of the wood type.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the MaterialColor of the inside of the wood.
     */
    public MaterialColor getWoodColor() {
        return woodColor;
    }

    /**
     * Returns the MaterialColor of the wood bark.
     */
    public MaterialColor getBarkColor() {
        return barkColor;
    }

    /**
     * Returns the texture key for the side of the stripped log.
     */
    public String getWoodTexture() {
        return woodTexture;
    }

    /**
     * Returns the texture key for the side of the unstripped log.
     */
    public String getBarkTexture() {
        return barkTexture;
    }

    /**
     * Returns the texture key for the top of the stripped log.
     */
    public String getTopTexture() {
        return topTexture;
    }

    /**
     * Returns the texture key for the breaking particles.
     */
    public String getParticleTexture() {
        return particleTexture;
    }
}
