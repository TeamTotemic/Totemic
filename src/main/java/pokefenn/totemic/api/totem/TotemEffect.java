package pokefenn.totemic.api.totem;

import java.util.Objects;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Base class for all Totem Effects.
 */
public abstract class TotemEffect { //TODO: This class needs a refactoring
    /**
     * The Totem Effect's registry name
     */
    protected final ResourceLocation name;
    /**
     * Whether this Totem Effect can be used with a Medicine Bag
     */
    protected final boolean portable;
    /**
     * The time in ticks between applications of the effect
     */
    protected final int interval;

    /**
     * @param name the Totem Effect's registry name.
     * @param portable whether this Totem Effect can be used with a Medicine Bag. In this case, override {@link #medicineBagEffect}.
     * @param interval the time in ticks between applications of the effect. This should ideally be a multiple of 80 or 20.
     */
    public TotemEffect(ResourceLocation name, boolean portable, int interval) {
        if(interval < 1)
            throw new IllegalArgumentException("The interval must be positive");
        this.name = Objects.requireNonNull(name);
        this.portable = portable;
        this.interval = interval;
    }

    /**
     * Performs the Totem Effect. This method is called every {@link #interval} ticks.
     *
     * @param world      the world
     * @param pos        the position
     * @param repetition the number of Totem Pole blocks that are carved with this effect
     * @param context    an object providing information about the Totem Base
     */
    public abstract void effect(Level world, BlockPos pos, int repetition, TotemEffectContext context);

    /**
     * Performs the Totem Effect when used in a Medicine Bag, if applicable. Override this method to make your effect work with Medicine Bags. This method is
     * called every {@link #interval} ticks.
     *
     * @param world       the world
     * @param player      the player who holds the Medicine Bag
     * @param medicineBag the Medicine Bag item stack
     * @param charge      time in ticks until the Medicine Bag is depleted, or -1 if it is a Creative Medicine Bag
     */
    public void medicineBagEffect(Level world, Player player, ItemStack medicineBag, int charge) {
    }

    /**
     * @return the translation key of the effect. By default it is given by "totem." followed by the registry name.
     */
    public String getDescriptionId() {
        return Util.makeDescriptionId("totem", name);
    }

    /**
     * @return a text component representing the effect's name
     */
    public MutableComponent getDisplayName() {
        return Component.translatable(getDescriptionId());
    }

    /**
     * @return the Totem Effect's registry name
     */
    public final ResourceLocation getName() {
        return name;
    }

    /**
     * @return whether this Totem Effect can be used with a Medicine Bag
     */
    public boolean isPortable() {
        return portable;
    }

    /**
     * @return the time in ticks between applications of the effect
     */
    public int getInterval() {
        return interval;
    }
}
