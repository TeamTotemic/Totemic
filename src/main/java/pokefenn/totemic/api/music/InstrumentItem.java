package pokefenn.totemic.api.music;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import pokefenn.totemic.api.TotemicAPI;

/**
 * Base class for simple music instrument items.
 *
 * <p>This class keeps track of a cooldown timer in the item stack's NBT
 * to ensure that the instrument is not played too often. This cooldown timer
 * is counted down each tick as long as the item is in the player's hotbar, using {@link #onUpdate}.
 */
public abstract class InstrumentItem extends Item
{
    /**
     * The NBT key for the cooldown timer
     */
    public static final String INSTR_COOLDOWN_KEY = "cooldown";

    protected MusicInstrument instrument;
    protected SoundEvent sound;

    /**
     * Constructs an ItemInstrument without specifying the instrument yet.
     * This allows registering the item before registering music instruments,
     * which is how Forge recommends it.
     */
    public InstrumentItem(Item.Properties properties)
    {
        super(properties);
    }

    /**
     * @return the corresponding music instrument
     */
    public MusicInstrument getInstrument()
    {
        return instrument;
    }

    /**
     * Sets the corresponding music instrument
     */
    public InstrumentItem setInstrument(MusicInstrument instrument)
    {
        this.instrument = Objects.requireNonNull(instrument);
        return this;
    }

    /**
     * @return the sound that plays when the instrument is played, if any
     */
    @Nullable
    public SoundEvent getSound()
    {
        return sound;
    }

    /**
     * Sets the sound to play whenever the instrument is played. Can be null.
     */
    public InstrumentItem setSound(@Nullable SoundEvent sound)
    {
        this.sound = sound;
        return this;
    }

    /**
     * Checks if the instrument is off cooldown and then plays music, putting it on cooldown.
     *
     * <p>This is the method you want to call whenever the player attempts to use the instrument,
     * for instance from onItemRightClick.
     * @param stack the item stack of the instrument
     * @param entity the entity that used the instrument
     * @param cooldown the cooldown in ticks that the instrument should be set on after successfully playing
     */
    protected void useInstrument(ItemStack stack, Entity entity, int cooldown)
    {
        CompoundNBT tag = stack.getOrCreateTag();

        if(tag.getInt(INSTR_COOLDOWN_KEY) == 0)
        {
            if(!entity.world.isRemote)
            {
                if(!entity.isSneaking())
                    playMusic(stack, entity);
                else
                    playSelector(stack, entity);
            }
            tag.putInt(INSTR_COOLDOWN_KEY, cooldown);
        }
    }

    /**
     * Plays music from this instrument.
     *
     * <p>The cooldown is not being checked in this method.
     * @param stack the item stack of the instrument
     * @param entity the entity that used the instrument
     */
    protected void playMusic(ItemStack stack, @Nonnull Entity entity)
    {
        TotemicAPI.get().music().playMusic(entity, instrument);
        spawnParticles((ServerWorld) entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), false);
        //The first parameter to playSound has to be null rather than entity, otherwise they will be unable to hear it.
        if(sound != null)
            entity.world.playSound(null, entity.getPosX(), entity.getPosY(), entity.getPosZ(), sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    /**
     * Plays this instrument as selector.
     *
     * <p>The cooldown is not being checked in this method.
     * @param stack the item stack of the instrument
     * @param entity the entity that used the instrument
     * @param bonusRadius additional radius
     * @param bonusMusic additional music amount
     */
    protected void playSelector(ItemStack stack, @Nonnull Entity entity)
    {
        TotemicAPI.get().music().playSelector(entity, instrument);
        spawnParticles((ServerWorld) entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), true);
        //The first parameter to playSound has to be null rather than entity, otherwise they will be unable to hear it.
        if(sound != null)
            entity.world.playSound(null, entity.getPosX(), entity.getPosY(), entity.getPosZ(), sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    /**
     * Spwans note or firework particles at the given location.
     * @param firework true for firework, false for note particles
     */
    protected void spawnParticles(ServerWorld world, double x, double y, double z, boolean firework)
    {
        world.spawnParticle(ParticleTypes.NOTE, x, y + 1.2, z, 6, 0.5, 0.0, 0.5, 0.0);
        if(firework)
            world.spawnParticle(ParticleTypes.FIREWORK, x, y + 1.2, z, 8, 0.5, 0.0, 0.5, 0.0);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
    {
        if(!world.isRemote && slot < PlayerInventory.getHotbarSize())
        {
            CompoundNBT tag = stack.getTag();
            if(tag == null)
                return;

            int cooldown = tag.getInt(INSTR_COOLDOWN_KEY);
            if(cooldown > 0)
            {
                cooldown--;
                tag.putInt(INSTR_COOLDOWN_KEY, cooldown);
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged || !ItemStack.areItemsEqual(oldStack, newStack);
    }
}
