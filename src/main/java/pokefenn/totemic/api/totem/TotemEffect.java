package pokefenn.totemic.api.totem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * Base class for all Totem Effects.<p>
 * Use the {@link RegisterTotemEffectsEvent} to register your Totem Effects. Please do <b>not</b> use
 * Forge's {@link RegistryEvent}!
 */
public abstract class TotemEffect extends ForgeRegistryEntry<TotemEffect> {
    /**
     * Whether this Totem Effect can be used with a Medicine Bag
     */
    protected final boolean portable;
    /**
     * The time in ticks between applications of the effect
     */
    protected final int interval;

    /**
     * @param portable whether this Totem Effect can be used with a Medicine Bag.
     * In this case, override {@link #medicineBagEffect}.
     * @param interval the time in ticks between applications of the effect
     */
    public TotemEffect(boolean portable, int interval) {
        if(interval < 1)
            throw new IllegalArgumentException("The interval must be positive");
        this.portable = portable;
        this.interval = interval;
    }

    /**
     * Performs the Totem Effect. This method is called every {@link #interval} ticks.
     * @param world the world
     * @param pos the position
     * @param repetition the number of Totem Pole blocks that are carved with this effect
     * @param context an object providing information about the Totem Base
     */
    public abstract void effect(IBlockReader world, BlockPos pos, int repetition, TotemEffectContext context);

    /**
     * Performs the Totem Effect when used in a Medicine Bag, if applicable. Override this method to make your effect work with Medicine Bags.
     * This method is called every {@link #interval} ticks.
     * @param world the world
     * @param player the player who holds the Medicine Bag
     * @param medicineBag the Medicine Bag item stack
     * @param charge time in ticks until the Medicine Bag is depleted, or -1 if it is a Creative Medicine Bag
     */
    public void medicineBagEffect(IBlockReader world, PlayerEntity player, ItemStack medicineBag, int charge) { }

    /**
     * @return the translation key of the effect. By default it is given by "totemic.totem." followed by the registry name.
     */
    public String getTranslationKey() {
        return Util.makeTranslationKey("totemic.totem", getRegistryName());
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
