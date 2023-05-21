package pokefenn.totemic.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;

/**
 * Represents a wood type that Totem Poles can be made out of.
 */
public final class TotemWoodType {
    private final ResourceLocation registryName;
    private final MaterialColor woodColor;
    private final MaterialColor barkColor;
    private final TagKey<Block> logTag;

    /**
     * Constructs a new TotemWoodType.
     * @param name      the TotemWoodType's registry name.
     * @param woodColor the MaterialColor of the log's inside.
     * @param barkColor the MaterialColor of the log's bark.
     * @param logTag    the tag containing all the log and wood blocks associated with this wood type (e.g. {@code minecraft:oak_logs})
     */
    public TotemWoodType(ResourceLocation name, MaterialColor woodColor, MaterialColor barkColor, TagKey<Block> logTag) {
        this.registryName = name;
        this.woodColor = woodColor;
        this.barkColor = barkColor;
        this.logTag = logTag;
    }

    /**
     * Returns the wood type's registry name.
     */
    public ResourceLocation getRegistryName() {
        return registryName;
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
     * Returns the block tag associated with this wood type.
     */
    public TagKey<Block> getLogTag() {
        return logTag;
    }

    @Override
    public String toString() {
        return registryName.toString();
    }
}
