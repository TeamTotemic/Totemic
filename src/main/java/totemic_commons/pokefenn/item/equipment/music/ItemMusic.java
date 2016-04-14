package totemic_commons.pokefenn.item.equipment.music;

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
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.client.PacketSound;
import totemic_commons.pokefenn.util.ItemUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public abstract class ItemMusic extends ItemTotemic
{
    public ItemMusic(String name)
    {
        super(name);
    }

    /**
     * If the instrument is off cooldown, plays music and puts it on cooldown.
     *
     * The cooldown is stored in the item stack's NBT data, and the onUpdate method is used to tick the cooldown down.
     * @param stack the item stack of the instrument
     * @param entity the entity that used the instrument
     * @param instrument the music instrument
     * @param cooldown the cooldown
     * @param soundName the name of the sound effect to play, or null if none
     */
    protected void useInstrument(ItemStack stack, Entity entity, MusicInstrument instrument, int cooldown, String soundName)
    {
        NBTTagCompound tag = ItemUtil.getOrCreateTag(stack);
        if(tag.getInteger(Strings.INSTR_COOLDOWN_KEY) == 0)
        {
            playMusic(stack, entity, instrument, soundName);
            tag.setInteger(Strings.INSTR_COOLDOWN_KEY, cooldown);

            tag.removeTag(Strings.INSTR_TIME_KEY); //Remove legacy NBT tag. TODO: Remove in a later version
        }
    }

    /**
     * Plays music from this instrument
     * @param stack the item stack of the instrument
     * @param entity the entity that used the instrument
     * @param instrument the music instrument
     * @param soundName the name of the sound effect to play, or null if none
     */
    protected void playMusic(ItemStack stack, Entity entity, MusicInstrument instrument, String soundName)
    {
        if(entity.worldObj.isRemote)
            return;

        WorldServer world = (WorldServer) entity.worldObj;
        if(!entity.isSneaking())
        {
            TotemUtil.playMusic(world, entity.posX, entity.posY, entity.posZ, instrument, getBonusRadius(stack, entity), getBonusMusic(stack, entity));
            particlesAllAround(world, entity.posX, entity.posY, entity.posZ, false);
        }
        else
        {
            TotemUtil.playMusicForSelector(world, entity.posX, entity.posY, entity.posZ, instrument, getBonusRadius(stack, entity));
            particlesAllAround(world, entity.posX, entity.posY, entity.posZ, true);
        }

        if(soundName != null)
            PacketHandler.sendAround(new PacketSound(entity, soundName), entity);
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

    protected int getBonusRadius(ItemStack stack, Entity entity)
    {
        return 0;
    }

    protected int getBonusMusic(ItemStack stack, Entity entity)
    {
        return 0;
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
