package totemic_commons.pokefenn.item.equipment.music;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
public class ItemFlute extends ItemMusic
{
    //Entities that have been tempted by the infused flute get stored in this weak set
    //so as to avoid adding the same AI task multiple times
    private final Set<Entity> temptedEntities = Collections.newSetFromMap(new WeakHashMap<>());

    public ItemFlute()
    {
        super(Strings.FLUTE_NAME, HandlerInitiation.flute);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            NBTTagCompound tag = ItemUtil.getOrCreateTag(itemStack);
            int time = tag.getInteger(Strings.INSTR_TIME_KEY);

            time++;
            if(time >= 5 && !player.isSneaking())
            {
                int bonusMusic = (itemStack.getItemDamage() == 1) ? world.rand.nextInt(3) : 0;
                time = 0;
                TotemUtil.playMusic(world, player.posX, player.posY, player.posZ, musicHandler, 0, bonusMusic);
                particlesAllAround((WorldServer)world, player.posX, player.posY, player.posZ, false);
                PacketHandler.sendAround(new PacketSound(player, "flute"), player);
            }
            if(time >= 5 && player.isSneaking())
            {
                time = 0;
                TotemUtil.playMusicForSelector(player.worldObj, player.posX, player.posY, player.posZ, musicHandler, 0);
                particlesAllAround((WorldServer)world, player.posX, player.posY, player.posZ, true);
                PacketHandler.sendAround(new PacketSound(player, "flute"), player);
            }
            if(itemStack.getItemDamage() == 1 && !player.isSneaking())
                for(Entity entity : EntityUtil.getEntitiesInRange(world, player.posX, player.posY, player.posZ, 2, 2))
                {
                    if(entity instanceof EntityAnimal || entity instanceof EntityVillager)
                    {
                        if(temptedEntities.contains(entity))
                            continue;

                        double d = (entity instanceof EntityAnimal) ? 1 : 0.5;
                        ((EntityLiving) entity).targetTasks.addTask(5, new EntityAITempt((EntityCreature) entity, d, this, false));

                        temptedEntities.add(entity);
                    }

                }

            tag.setInteger(Strings.INSTR_TIME_KEY, time);
        }
        return itemStack;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if(stack.getItemDamage() == 1)
            return "item.totemic:fluteInfused";
        else
            return "item.totemic:flute";
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> list)
    {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return stack.getItemDamage() == 1;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged;
    }

    private void particlesAllAround(WorldServer world, double x, double y, double z, boolean firework)
    {
        TotemUtil.particlePacket(world, EnumParticleTypes.NOTE, x, y + 1.2D, z, 6, 0.5D, 0.0D, 0.5D, 0.0D);

        if(firework)
        {
            TotemUtil.particlePacket(world, EnumParticleTypes.FIREWORKS_SPARK, x, y + 1.2D, z, 8, 0.5D, 0.0D, 0.5D, 0.0D);
        }
    }

}
