package totemic_commons.pokefenn.item.equipment.music;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.client.PacketSound;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.util.EntityUtil;
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
            EntityPlayer player = (EntityPlayer) entity;
            RayTraceResult block = EntityUtil.raytraceFromEntity(world, player, true, 5);
            if(block == null)
            {
                NBTTagCompound tag = ItemUtil.getOrCreateTag(stack);
                int time = tag.getInteger(Strings.INSTR_TIME_KEY);

                time++;
                if(time >= 4 && !player.isSneaking())
                {
                    time = 0;
                    TotemUtil.playMusic(world, player.posX, player.posY, player.posZ, musicHandler, 0, 0);
                    particlesAllAround((WorldServer)world, player.posX, player.posY, player.posZ, false);
                    PacketHandler.sendAround(new PacketSound(player, "rattle"), player);
                }
                if(time >= 4 && player.isSneaking())
                {
                    time = 0;
                    TotemUtil.playMusicForSelector(player.worldObj, player.posX, player.posY, player.posZ, musicHandler, 0);
                    particlesAllAround((WorldServer)world, player.posX, player.posY, player.posZ, true);
                    PacketHandler.sendAround(new PacketSound(player, "rattle"), player);
                }

                tag.setInteger(Strings.INSTR_TIME_KEY, time);
            }
        }

        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        if(!player.isSwingInProgress)
            player.swingItem();
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
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
