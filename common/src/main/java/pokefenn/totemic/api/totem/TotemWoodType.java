package pokefenn.totemic.api.totem;

import java.util.Objects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import pokefenn.totemic.api.TotemicAPI;

/**
 * Represents a wood type that Totem Poles can be made out of.
 * @param woodColor the MapColor of the log's inside.
 * @param barkColor the MapColor of the log's bark.
 * @param logTag    the tag containing all the log and wood blocks associated with this wood type (e.g. {@code minecraft:oak_logs}).<br>
 *                  Any of the tagged blocks will be recognized as this wood type by the Totem Whittling Knife.
 */
public record TotemWoodType(MapColor woodColor, MapColor barkColor, TagKey<Block> logTag) {
    public TotemWoodType {
        Objects.requireNonNull(woodColor);
        Objects.requireNonNull(barkColor);
        Objects.requireNonNull(logTag);
    }

    /**
     * Returns the wood type's registry name.
     */
    public ResourceLocation getRegistryName() {
        return TotemicAPI.get().registry().woodTypes().getKey(this);
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
    }
}
