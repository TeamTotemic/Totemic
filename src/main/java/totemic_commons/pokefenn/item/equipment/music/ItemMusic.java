package totemic_commons.pokefenn.item.equipment.music;

import java.util.Objects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.ItemUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Base class for simple music instrument items.
 *
 * Keeps track of a cooldown timer in the item stack's NBT
 * to ensure that the item is not used too often.
 */
public abstract class ItemMusic extends ItemTotemic
{
    public final MusicInstrument instrument;
    public final String soundName;

    /**
     * @param name unlocalized name
     * @param instrument the corresponding music instrument
     * @param sound the sound to play whenever the instrument is played. Can be null.
     */
    public ItemMusic(String name, MusicInstrument instrument, String soundName)
    {
        super(name);
        this.instrument = Objects.requireNonNull(instrument);
        this.soundName = soundName;
    }

    /**
     * If the instrument is off cooldown, plays music and puts it on cooldown.
     *
     * Call this method when your item is being used in some way (e.g. onItemRightClick).
     * The cooldown is stored in the item stack's NBT data, and the onUpdate method is used to tick the cooldown down.
     * @param stack the item stack of the instrument
     * @param entity the entity that used the instrument
     * @param cooldown the cooldown in ticks after the instrument has been played
     * @param bonusRadius additional radius
     * @param bonusMusic additional music amount
     */
    protected void useInstrument(ItemStack stack, Entity entity, int cooldown, int bonusRadius, int bonusMusic)
    {
        NBTTagCompound tag = ItemUtil.getOrCreateTag(stack);
        if(tag.getInteger(Strings.INSTR_COOLDOWN_KEY) == 0)
        {
            playMusic(stack, entity, bonusRadius, bonusMusic);
            tag.setInteger(Strings.INSTR_COOLDOWN_KEY, cooldown);
        }
    }

    /**
     * Plays music from this instrument
     * @param stack the item stack of the instrument
     * @param entity the entity that used the instrument
     * @param bonusRadius additional radius
     * @param bonusMusic additional music amount
     */
    protected void playMusic(ItemStack stack, Entity entity, int bonusRadius, int bonusMusic)
    {
        if(entity.worldObj.isRemote)
            return;

        WorldServer world = (WorldServer) entity.worldObj;
        if(!entity.isSneaking())
        {
            TotemUtil.playMusic(world, entity.posX, entity.posY, entity.posZ, instrument, bonusRadius, bonusMusic);
            particlesAllAround(world, entity.posX, entity.posY, entity.posZ, false);
        }
        else
        {
            TotemUtil.playMusicForSelector(world, entity.posX, entity.posY, entity.posZ, instrument, bonusRadius);
            particlesAllAround(world, entity.posX, entity.posY, entity.posZ, true);
        }

        if(soundName != null)
            TotemUtil.playSound(entity, soundName, 1.0f, 1.0f);
    }

    /**
     * Spwans music particles at the given location
     */
    protected void particlesAllAround(WorldServer world, double x, double y, double z, boolean firework)
    {
        world.spawnParticle(EnumParticleTypes.NOTE, x, y + 1.2D, z, 6, 0.5D, 0.0D, 0.5D, 0.0D);
        if(firework)
            world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, x, y + 1.2D, z, 8, 0.5D, 0.0D, 0.5D, 0.0D);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected)
    {
        if(!world.isRemote && slot < InventoryPlayer.getHotbarSize())
        {
            NBTTagCompound tag = stack.getTagCompound();
            if(tag == null)
                return;

            int cooldown = tag.getInteger(Strings.INSTR_COOLDOWN_KEY);
            if(cooldown > 0)
            {
                cooldown--;
                tag.setInteger(Strings.INSTR_COOLDOWN_KEY, cooldown);
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged;
    }
}
