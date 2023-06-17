package pokefenn.totemic.api.totem;

import java.util.Objects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import pokefenn.totemic.api.TotemicAPI;

/**
 * Represents a wood type that Totem Poles can be made out of.
 */
public final class TotemWoodType {
    private final MaterialColor woodColor;
    private final MaterialColor barkColor;
    private final TagKey<Block> logTag;

    /**
     * Constructs a new TotemWoodType.
     * @param woodColor the MaterialColor of the log's inside.
     * @param barkColor the MaterialColor of the log's bark.
     * @param logTag    the tag containing all the log and wood blocks associated with this wood type (e.g. {@code minecraft:oak_logs}).<br>
     *                  Any of the tagged blocks will be recognized as this wood type by the Totem Whittling Knife.
     */
    public TotemWoodType(MaterialColor woodColor, MaterialColor barkColor, TagKey<Block> logTag) {
        this.woodColor = Objects.requireNonNull(woodColor);
        this.barkColor = Objects.requireNonNull(barkColor);
        this.logTag = Objects.requireNonNull(logTag);
    }

    /**
     * Returns the wood type's registry name.
     */
    public ResourceLocation getRegistryName() {
        return TotemicAPI.get().registry().woodTypes().getKey(this);
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
     *
     * Any of the blocks in this tag will be recognized as this wood type by the Totem Whittling Knife.
     */
    public TagKey<Block> getLogTag() {
        return logTag;
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
    }
}
