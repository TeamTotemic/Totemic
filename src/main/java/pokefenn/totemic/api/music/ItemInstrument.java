package pokefenn.totemic.api.music;

import java.util.Objects;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import pokefenn.totemic.api.TotemicAPI;

/**
 * Base class for simple music instrument items.
 *
 * <p>This class keeps track of a cooldown timer in the item stack's NBT
 * to ensure that the instrument is not played too often. This cooldown timer
 * is counted down each tick as long as the item is in the player's hotbar, using {@link #onUpdate}.
 */
public abstract class ItemInstrument extends Item
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
    public ItemInstrument()
    { }

    /**
     * @param instrument the corresponding music instrument
     * @param sound the sound to play whenever the instrument is played. Can be null.
     */
    public ItemInstrument(MusicInstrument instrument, @Nullable SoundEvent sound)
    {
        this.instrument = Objects.requireNonNull(instrument);
        this.sound = sound;
    }

    /**
     * @return the corresponding music instrument
     */
    public MusicInstrument getInstrument()
    {
        return instrument;
    }

    public ItemInstrument setInstrument(MusicInstrument instrument)
    {
        this.instrument = instrument;
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

    public ItemInstrument setSound(@Nullable SoundEvent sound)
    {
        this.sound = sound;
        return this;
    }

    /**
     * Plays music and puts the instrument on the specified cooldown.
     * Does nothing if the instrument is currently on cooldown.
     *
     * <p>Call this method when your item is being used in some way (e.g. from onItemRightClick).
     * It is not automatically called.
     * @param stack the item stack of the instrument
     * @param entity the entity that used the instrument
     * @param cooldown the cooldown in ticks after the instrument has been played
     * @param bonusRadius additional radius
     * @param bonusMusic additional music amount
     */
    protected void useInstrument(ItemStack stack, Entity entity, int cooldown, int bonusRadius, int bonusMusic)
    {
        if(!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        NBTTagCompound tag = stack.getTagCompound();

        if(tag.getInteger(INSTR_COOLDOWN_KEY) == 0)
        {
            playMusic(stack, entity, bonusRadius, bonusMusic);
            tag.setInteger(INSTR_COOLDOWN_KEY, cooldown);
        }
    }

    /**
     * Plays music from this instrument, regardless of cooldown
     * @param stack the item stack of the instrument
     * @param entity the entity that used the instrument
     * @param bonusRadius additional radius
     * @param bonusMusic additional music amount
     */
    protected void playMusic(ItemStack stack, Entity entity, int bonusRadius, int bonusMusic)
    {
        if(entity.world.isRemote)
            return;

        WorldServer world = (WorldServer) entity.world;
        if(!entity.isSneaking())
        {
            TotemicAPI.get().music().playMusic(world, entity.posX, entity.posY, entity.posZ, instrument, bonusRadius, bonusMusic);
            spawnParticles(world, entity.posX, entity.posY, entity.posZ, false);
        }
        else
        {
            TotemicAPI.get().music().playMusicForSelector(world, entity.posX, entity.posY, entity.posZ, instrument, bonusRadius);
            spawnParticles(world, entity.posX, entity.posY, entity.posZ, true);
        }

        if(sound != null)
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    /**
     * Spwans music particles at the given location
     */
    protected void spawnParticles(WorldServer world, double x, double y, double z, boolean firework)
    {
        world.spawnParticle(EnumParticleTypes.NOTE, x, y + 1.2, z, 6, 0.5, 0.0, 0.5, 0.0);
        if(firework)
            world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, x, y + 1.2, z, 8, 0.5, 0.0, 0.5, 0.0);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
    {
        if(!world.isRemote && slot < InventoryPlayer.getHotbarSize())
        {
            NBTTagCompound tag = stack.getTagCompound();
            if(tag == null)
                return;

            int cooldown = tag.getInteger(INSTR_COOLDOWN_KEY);
            if(cooldown > 0)
            {
                cooldown--;
                tag.setInteger(INSTR_COOLDOWN_KEY, cooldown);
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged || !ItemStack.areItemsEqual(oldStack, newStack);
    }
}
