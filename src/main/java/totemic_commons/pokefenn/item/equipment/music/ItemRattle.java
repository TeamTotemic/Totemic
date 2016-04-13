package totemic_commons.pokefenn.item.equipment.music;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.client.PacketSound;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.util.ItemUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemRattle extends ItemMusic
{
    public ItemRattle()
    {
        super(Strings.CEREMONY_RATTLE_NAME, HandlerInitiation.rattle);
        setMaxStackSize(1);
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack)
    {
        World world = entity.worldObj;
        if(!world.isRemote && entity instanceof EntityPlayer && !(entity instanceof FakePlayer))
        {
            NBTTagCompound tag = ItemUtil.getOrCreateTag(stack);
            if(tag.getInteger(Strings.INSTR_COOLDOWN_KEY) == 0)
            {
                playMusic(stack, (WorldServer) world, (EntityPlayer) entity);
                tag.setInteger(Strings.INSTR_COOLDOWN_KEY, 16);

                tag.removeTag(Strings.INSTR_TIME_KEY); //Remove legacy NBT tag
            }
        }

        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if(!player.isSwingInProgress)
            player.swingItem();
        return stack;
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

    private void playMusic(ItemStack stack, WorldServer world, EntityPlayer player)
    {
        if(!player.isSneaking())
        {
            TotemUtil.playMusic(world, player.posX, player.posY, player.posZ, musicHandler, 0, 0);
            particlesAllAround(world, player.posX, player.posY, player.posZ, false);
            PacketHandler.sendAround(new PacketSound(player, "rattle"), player);
        }
        else
        {
            TotemUtil.playMusicForSelector(player.worldObj, player.posX, player.posY, player.posZ, musicHandler, 0);
            particlesAllAround(world, player.posX, player.posY, player.posZ, true);
            PacketHandler.sendAround(new PacketSound(player, "rattle"), player);
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged;
    }

    public void particlesAllAround(WorldServer world, double x, double y, double z, boolean firework)
    {
        world.spawnParticle(EnumParticleTypes.NOTE, x, y + 1.2D, z, 6, 0.5D, 0.0D, 0.5D, 0.0D);

        if(firework)
        {
            world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, x, y + 1.2D, z, 8, 0.5D, 0.0D, 0.5D, 0.0D);
        }
    }

}
