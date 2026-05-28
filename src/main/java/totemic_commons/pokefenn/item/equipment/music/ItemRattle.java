package totemic_commons.pokefenn.item.equipment.music;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.util.ItemUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemRattle extends ItemMusic
{
    //This int will hold the amount of nearby poles, which are sticks and buffalo heads. Each pole will increase the music made.
    //public int headPoles;

    public ItemRattle()
    {
        super(Strings.CEREMONY_RATTLE_NAME, HandlerInitiation.rattle);
        setMaxStackSize(1);
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack)
    {
        final int cooldownTicks = 16;

        World world = entity.worldObj;
        if(!world.isRemote && entity instanceof EntityPlayer && !(entity instanceof FakePlayer))
        {
            EntityPlayer player = (EntityPlayer) entity;
            NBTTagCompound tag = ItemUtil.getOrCreateTag(stack);
            long lastPlayed = tag.getLong(Strings.INSTR_PLAYED_KEY);

            if(lastPlayed + cooldownTicks <= world.getTotalWorldTime())
            {
                if(!player.isSneaking())
                {
                    TotemUtil.playMusic(world, player.posX, player.posY, player.posZ, musicHandler, 0, 0);
                    particlesAllAround((WorldServer)world, player.posX, player.posY, player.posZ, false);
                    world.playSoundAtEntity(player, "totemic:rattle", 1.0F, 1.0F);
                }
                else
                {
                    TotemUtil.playMusicForSelector(player.worldObj, player.posX, player.posY, player.posZ, musicHandler, 0);
                    particlesAllAround((WorldServer)world, player.posX, player.posY, player.posZ, true);
                    world.playSoundAtEntity(player, "totemic:rattle", 1.0F, 1.0F);
                }
                tag.setLong(Strings.INSTR_PLAYED_KEY, world.getTotalWorldTime());

                tag.removeTag(Strings.INSTR_TIME_KEY);
            }
        }

        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if(!player.isSwingInProgress)
            player.swingItem();
        return stack;
    }

    public void particlesAllAround(WorldServer world, double x, double y, double z, boolean firework)
    {
        TotemUtil.particlePacket(world, "note", x, y + 1.2D, z, 6, 0.5D, 0.0D, 0.5D, 0.0D);

        if(firework)
        {
            TotemUtil.particlePacket(world, "fireworksSpark", x, y + 1.2D, z, 8, 0.5D, 0.0D, 0.5D, 0.0D);
        }
    }

}
