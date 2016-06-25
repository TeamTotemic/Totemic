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
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.music.ItemInstrument;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemFlute extends ItemInstrument
{
    //Entities that have been tempted by the infused flute get stored in this weak set
    //so as to avoid adding the same AI task multiple times
    private final Set<Entity> temptedEntities = Collections.newSetFromMap(new WeakHashMap<>());

    public ItemFlute()
    {
        super(HandlerInitiation.flute, "totemic:flute");
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.FLUTE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            useInstrument(stack, player, 20, 0, (stack.getItemDamage() == 1) ? world.rand.nextInt(3) : 0);

            if(stack.getItemDamage() == 1 && !player.isSneaking())
                temptEntities(world, player.posX, player.posY, player.posZ);
        }
        return stack;
    }

    private void temptEntities(World world, double x, double y, double z)
    {
        for(EntityLiving entity : EntityUtil.getEntitiesInRange(EntityLiving.class, world, x, y, z, 2, 2))
        {
            if(entity instanceof EntityAnimal || entity instanceof EntityVillager)
            {
                if(temptedEntities.contains(entity))
                    continue;

                double speed = (entity instanceof EntityAnimal) ? 1 : 0.5;
                entity.targetTasks.addTask(5, new EntityAITempt((EntityCreature) entity, speed, this, false));

                temptedEntities.add(entity);
            }

        }
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
}
