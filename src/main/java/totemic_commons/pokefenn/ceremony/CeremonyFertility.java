package totemic_commons.pokefenn.ceremony;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
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
    //Weak set to keep track which villagers have been affected by the ceremony
    private static final Set<EntityVillager> matedVillagers = Collections.newSetFromMap(new WeakHashMap<>());

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
                if(villager.getGrowingAge() == 0 && !matedVillagers.contains(villager) && !villager.isMating() && consumeBreedingItem(world, aabb, villager))
                {
                    matedVillagers.add(villager);
                    villager.tasks.addTask(0, new EntityAIVillagerFertility(villager));
                    world.setEntityState(villager, (byte) 12); //Spawns heart particles
                    return; // Limit to one animal or villager per second
                }
            }
        }
    }

    private boolean consumeBreedingItem(World world, AxisAlignedBB aabb, EntityAnimal animal)
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

    private boolean consumeBreedingItem(World world, AxisAlignedBB aabb, EntityVillager villager)
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

    // A replacement for EntityAIVillagerMate that makes villagers mate regardless of the number of doors in a village, etc.
    private static class EntityAIVillagerFertility extends EntityAIBase
    {
        private final EntityVillager villager;
        private EntityVillager mate;
        private final World world;
        private int matingTimeout;

        EntityAIVillagerFertility(EntityVillager villager)
        {
            this.villager = villager;
            this.world = villager.worldObj;
            this.setMutexBits(3);
        }
        
        @Override
        public boolean shouldExecute()
        {
            if(this.villager.getGrowingAge() == 0)
            {
                EntityVillager mate = (EntityVillager) this.world.findNearestEntityWithinAABB(EntityVillager.class, this.villager.boundingBox.expand(8.0D, 3.0D, 8.0D), this.villager);
                if (mate == null)
                    return false;
                else
                {
                    this.mate = mate;
                    return mate.getGrowingAge() == 0 && matedVillagers.contains(mate);
                }
            }
            else
            {
                return false;
            }
        }

        @Override
        public void startExecuting()
        {
            this.matingTimeout = 300;
            this.villager.setMating(true);
        }

        @Override
        public void resetTask()
        {
            this.mate = null;
            this.villager.setMating(false);
            matedVillagers.remove(this.villager);
            matedVillagers.remove(this.mate);
            this.villager.tasks.removeTask(this); // FIXME: this causes infinite recursion, can't do it like that
        }

        @Override
        public boolean continueExecuting()
        {
            return this.matingTimeout >= 0 && this.villager.getGrowingAge() == 0 && matedVillagers.contains(mate);
        }

        @Override
        public void updateTask()
        {
            --this.matingTimeout;
            this.villager.getLookHelper().setLookPositionWithEntity(this.mate, 10.0F, 30.0F);

            if (this.villager.getDistanceSqToEntity(this.mate) > 2.25D)
            {
                this.villager.getNavigator().tryMoveToEntityLiving(this.mate, 0.25D);
            }
            else if (this.matingTimeout == 0 && this.mate.isMating())
            {
                this.giveBirth();
            }

            if (this.villager.getRNG().nextInt(35) == 0)
            {
                this.world.setEntityState(this.villager, (byte)12);
            }
        }

        private void giveBirth()
        {
            EntityVillager entityvillager = this.villager.createChild(this.mate);
            this.mate.setGrowingAge(6000);
            this.villager.setGrowingAge(6000);
            entityvillager.setGrowingAge(-24000);
            entityvillager.setLocationAndAngles(this.villager.posX, this.villager.posY, this.villager.posZ, 0.0F, 0.0F);
            this.world.spawnEntityInWorld(entityvillager);
            this.world.setEntityState(entityvillager, (byte)12);
        }
    }
}
