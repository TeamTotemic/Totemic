package totemic_commons.pokefenn.ceremony;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

public class CeremonyFertility extends Ceremony
{
    // Weak map to keep track which villagers have been affected by the ceremony
    private static final Map<EntityVillager, EntityAIVillagerFertility> villagerFertilityTasks = new WeakHashMap<>();

    public CeremonyFertility(String modid, String name, int musicNeeded, int maxStartupTime, int effectTime, int musicPer5, MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, effectTime, musicPer5, instruments);
    }

    @Override
    public void effect(World world, int x, int y, int z)
    {
        if(!world.isRemote && world.getTotalWorldTime() % 20 == 0)
        {
            transformSaplings(world, x, y, z);
            breedAnimalsAndVillagers(world, x, y, z);
        }
    }

    private void transformSaplings(World world, int x, int y, int z)
    {
        final int radius = 6;
        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    Block block = world.getBlock(x + i, y + j, z + k);
                    if(block instanceof BlockSapling && block != ModBlocks.totemSapling)
                    {
                        world.setBlock(x + i, y + j, z + k, ModBlocks.totemSapling, 0, 3);
                        TotemUtil.particlePacket(world, "happyVillager", x + 0.5, y + 0.5, z + 0.5, 10, 1.0, 0.5, 1.0, 1.0);
                        return;
                    }
                }
    }

    private void breedAnimalsAndVillagers(World world, int x, int y, int z)
    {
        final int radius = 8;
        AxisAlignedBB aabb = EntityUtil.getAABBAround(x, y, z, radius, radius);
        for(EntityLiving entity: world.selectEntitiesWithinAABB(EntityLiving.class, aabb, e -> e instanceof EntityAnimal || e instanceof EntityVillager))
        {
            if(entity instanceof EntityAnimal)
            {
                EntityAnimal animal = (EntityAnimal) entity;
                if(animal.getGrowingAge() == 0 && !animal.isInLove() && consumeBreedingItem(world, aabb, animal))
                {
                    animal.func_146082_f(null); // sets the animal in love
                    return; // Limit to one animal or villager per second
                }
            }
            else if(entity instanceof EntityVillager)
            {
                EntityVillager villager = (EntityVillager) entity;
                if(villager.getGrowingAge() == 0 && !villager.isMating())
                {
                    // Add Fertility AI task if the villager does not already have it.
                    //
                    // Since a task cannot safely remove itself in 1.7.10 (leads to inifite recursion or
                    // ConcurrentModificationException), the villagers will keep the task indefinitely but have it only
                    // activate when this Ceremony is performed.
                    // We use the weak map to avoid adding the task to the same villager multiple times and look it up
                    // if it's already added.
                    EntityAIVillagerFertility task = villagerFertilityTasks.get(villager);
                    if(task == null)
                    {
                        task = new EntityAIVillagerFertility(villager);
                        villager.tasks.addTask(1, task);
                        villagerFertilityTasks.put(villager, task);
                    }

                    if(!task.isActive() && consumeBreedingItem(world, aabb, villager))
                    {
                        task.setActive(true);
                        world.setEntityState(villager, (byte) 12); //Spawns heart particles
                        return; // Limit to one animal or villager per second
                    }
                }
            }
        }
    }

    private static boolean consumeBreedingItem(World world, AxisAlignedBB aabb, EntityAnimal animal)
    {
        Optional<EntityItem> itemE = findItemEntity(world, aabb, animal::isBreedingItem);
        if(itemE.isPresent())
        {
            if(world.rand.nextInt(3) < 2)
                EntityUtil.shrinkItemEntity(itemE.get());
            return true;
        }
        else
            return false;
    }

    private static boolean consumeBreedingItem(World world, AxisAlignedBB aabb, EntityVillager villager)
    {
        Optional<EntityItem> itemE = findItemEntity(world, aabb, item -> item.getItem() == Items.emerald);
        if(itemE.isPresent())
        {
            EntityUtil.shrinkItemEntity(itemE.get());
            return true;
        }
        else
            return false;
    }

    private static Optional<EntityItem> findItemEntity(World world, AxisAlignedBB aabb, Predicate<ItemStack> predicate)
    {
        List<EntityItem> list = world.selectEntitiesWithinAABB(EntityItem.class, aabb, entity -> predicate.test(((EntityItem) entity).getEntityItem()));
        return !list.isEmpty() ? Optional.of(list.get(0)) : Optional.empty();
    }

    // A copy of EntityAIVillagerMate that makes villagers mate if active, regardless of the number of doors in a village, etc.
    private static class EntityAIVillagerFertility extends EntityAIBase
    {
        private EntityVillager villagerObj;
        private EntityVillager mate;
        private World worldObj;
        private int matingTimeout;

        // The task will only run when this is set to true, which is done by the Fertility ceremony.
        private boolean isActive = false;

        EntityAIVillagerFertility(EntityVillager villager)
        {
            this.villagerObj = villager;
            this.worldObj = villager.worldObj;
            this.setMutexBits(3);
        }

        boolean isActive()
        {
            return isActive;
        }

        void setActive(boolean isActive)
        {
            this.isActive = isActive;
        }
        
        @Override
        public boolean shouldExecute()
        {
            if (!this.isActive || this.villagerObj.getGrowingAge() != 0)
            {
                return false;
            }
            else
            {
                // TODO: This will always pick the closest nearby villager, even if they aren't able to mate. Not sure if this is problematic.
                Entity mate = this.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.villagerObj.boundingBox.expand(8.0D, 3.0D, 8.0D), this.villagerObj);

                if (mate == null)
                {
                    return false;
                }
                else
                {
                    this.mate = (EntityVillager)mate;
                    return this.mate.getGrowingAge() == 0;
                }
            }
        }

        @Override
        public void startExecuting()
        {
            this.matingTimeout = 300;
            this.villagerObj.setMating(true);
        }

        @Override
        public void resetTask()
        {
            this.isActive = false;
            this.mate = null;
            this.villagerObj.setMating(false);
        }

        @Override
        public boolean continueExecuting()
        {
            return this.isActive && this.matingTimeout >= 0 && this.villagerObj.getGrowingAge() == 0;
        }

        @Override
        public void updateTask()
        {
            --this.matingTimeout;
            this.villagerObj.getLookHelper().setLookPositionWithEntity(this.mate, 10.0F, 30.0F);
    
            if (this.villagerObj.getDistanceSqToEntity(this.mate) > 2.25D)
            {
                this.villagerObj.getNavigator().tryMoveToEntityLiving(this.mate, 0.25D);
            }
            else if (this.matingTimeout == 0 && this.mate.isMating())
            {
                this.giveBirth();
            }
    
            if (this.villagerObj.getRNG().nextInt(35) == 0)
            {
                this.worldObj.setEntityState(this.villagerObj, (byte)12);
            }
        }

        private void giveBirth()
        {
            EntityVillager entityvillager = this.villagerObj.createChild(this.mate);
            this.mate.setGrowingAge(6000);
            this.villagerObj.setGrowingAge(6000);
            entityvillager.setGrowingAge(-24000);
            entityvillager.setLocationAndAngles(this.villagerObj.posX, this.villagerObj.posY, this.villagerObj.posZ, 0.0F, 0.0F);
            this.worldObj.spawnEntityInWorld(entityvillager);
            this.worldObj.setEntityState(entityvillager, (byte)12);
        }
    }
}
