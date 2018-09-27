package pokefenn.totemic.item.equipment.music;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.music.ItemInstrument;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.init.ModSounds;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.util.EntityUtil;

public class ItemFlute extends ItemInstrument
{
    //Entities that have been tempted by the infused flute get stored in this weak set
    //so as to avoid adding the same AI task multiple times
    private final Set<Entity> temptedEntities = Collections.newSetFromMap(new WeakHashMap<>());

    public ItemFlute()
    {
        setSound(ModSounds.flute);

        setRegistryName(Strings.FLUTE_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.FLUTE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (stack.getItemDamage() == 1)
            tooltip.add(I18n.format(getUnlocalizedName() +".tooltip1"));
        tooltip.add(I18n.format(getUnlocalizedName() + ".tooltip0"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote)
        {
            useInstrument(stack, player, 20);

            if (stack.getItemDamage() == 1 && !player.isSneaking())
                temptEntities(world, player.posX, player.posY, player.posZ);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack)
    {
        return stack.getItemDamage() == 1 ? EnumRarity.UNCOMMON : EnumRarity.COMMON;
    }

    @Override
    protected void playMusic(ItemStack stack, Entity entity)
    {
        int bonusMusic = (stack.getItemDamage() == 1) ? entity.world.rand.nextInt(2) : 0;
        TotemicAPI.get().music().playMusic(entity.world, entity.posX, entity.posY, entity.posZ, entity, instrument, MusicAPI.DEFAULT_RANGE, instrument.getBaseOutput() + bonusMusic);
        spawnParticles((WorldServer) entity.world, entity.posX, entity.posY, entity.posZ, false);
        if (sound != null)
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    private void temptEntities(World world, double x, double y, double z)
    {
        for (EntityLiving entity : EntityUtil.getEntitiesInRange(EntityLiving.class, world, x, y, z, 2, 2,
                entity -> ((entity instanceof EntityAnimal && entity.getNavigator() instanceof PathNavigateGround) || entity instanceof EntityVillager)
                          && !temptedEntities.contains(entity)))
        {
            double speed = (entity instanceof EntityAnimal) ? 1 : 0.5;
            entity.targetTasks.addTask(5, new EntityAITempt((EntityCreature) entity, speed, this, false));

            temptedEntities.add(entity);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if (stack.getItemDamage() == 1)
            return "item." + Strings.RESOURCE_PREFIX + "infused_flute";
        else
            return "item." + Strings.RESOURCE_PREFIX + "flute";
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        if (isInCreativeTab(tab))
        {
            list.add(new ItemStack(this, 1, 0));
            list.add(new ItemStack(this, 1, 1));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return stack.getItemDamage() == 1;
    }
}
