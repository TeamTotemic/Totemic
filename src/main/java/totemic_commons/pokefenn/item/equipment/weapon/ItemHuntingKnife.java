package totemic_commons.pokefenn.item.equipment.weapon;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.item.equipment.EquipmentMaterials;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemHuntingKnife extends ItemSword
{
    public ItemHuntingKnife()
    {
        super(EquipmentMaterials.huntingKnife);
        setCreativeTab(Totemic.tabsTotem);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.HUNTING_KNIFE_NAME);
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase entity1, EntityLivingBase entity2)
    {
        if(entity1 instanceof EntityAnimal)
            ((EntityAnimal) entity1).tasks.addTask(0, new EntityAIAvoidEntity<>((EntityCreature) entity1, EntityPlayer.class, 16.0F, 3D, 1.6D));

        itemStack.damageItem(1, entity2);
        return true;
    }

    /*
    public void onUpdate(ItemStack itemStack, World world, Entity player, int par4, boolean par5)
    {
        if(!world.isRemote)
        {
            if(world.getWorldTime() % 10L == 0)
            {
                if(player instanceof EntityPlayer)
                {
                    if(((EntityPlayer) player).getHeldItem() != null && ((EntityPlayer) player).getHeldItem().getItem() == ModItems.huntingKnife)
                    {
                        if(EntityUtil.getEntitiesInRange(world, player.posX, player.posY, player.posZ, 5, 5) != null)
                        {
                            for(Entity entity : EntityUtil.getEntitiesInRange(world, player.posX, player.posY, player.posZ, 5, 5))
                            {
                                if(entity instanceof EntityAnimal)
                                {
                                    System.out.println("foobar?");
                                    ((EntityAnimal) entity).tasks.addTask(0, new EntityAIAvoidEntity((EntityCreature) entity, EntityPlayer.class, 16.0F, 1D, 1.33D));
                                }

                            }
                        }
                    }
                }
            }
        }
    }
    */
}
