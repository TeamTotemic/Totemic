package pokefenn.totemic.ceremony;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.util.EntityUtil;

import java.util.Random;

public class CeremonyDanseMacabre extends Ceremony
{

    public CeremonyDanseMacabre(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, CeremonyEffectContext context)
    {
        int radius = 6;

        if (!world.isRemote && context.getTime() % 20 == 0)
        {
            for (EntityItem entity : EntityUtil.getEntitiesInRange(EntityItem.class, world, pos, radius, radius))
            {
                EntityItem item = entity;
                if (item.getItem().getItem() == Items.ROTTEN_FLESH)
                {
                    if (world.rand.nextInt(4) == 0)
                    {
                        if(entity.dimension == -1)
                        {
                            EntityPigZombie pigZombie = new EntityPigZombie(world);
                            summon(world, pigZombie, item, pos);
                        }
                        else if (world.rand.nextInt(10) == 0)
                        {
                            EntityZombieVillager zombieVillager = new EntityZombieVillager(world);
                            summon(world, zombieVillager, item, pos);

                        } else
                        {
                            EntityZombie zombie = new EntityZombie(world);
                            summon(world, zombie, item, pos);
                        }
                    }
                }
            }
        }

    }

    void summon(World world, EntityZombie zombie, EntityItem item, BlockPos pos)
    {
        Random rand = world.rand;
        double x = pos.getX() + rand.nextInt(3);
        int y = pos.getY();
        int z = pos.getZ();
        BlockPos spawnPos = new BlockPos(x, y, z);

        if (world.isAirBlock(spawnPos) && world.isAirBlock(new BlockPos(x, y + 1, z)))
        {
            zombie.setPosition(x, y, z);
            zombie.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20 * 30, 1));
            zombie.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20 * 30, 1));
            world.spawnEntity(zombie);
            if (item.getItem().getCount() == 1)
                item.setDead();
            else
            {
                ItemStack stack = item.getItem().copy();
                stack.shrink(1);
                item.setItem(stack);
            }
            spawnParticles(world, x, y, z);
        }
    }

    @Override
    public int getEffectTime()
    {
        return LONG;
    }

    @Override
    public int getMusicPer5()
    {
        return 6;
    }

    private void spawnParticles(World world, double x, double y, double z)
    {
        double dx = world.rand.nextGaussian();
        double dy = world.rand.nextGaussian() * 0.5;
        double dz = world.rand.nextGaussian();
        double velY = world.rand.nextGaussian();
        for (int i = 0; i < 20; i++)
            ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, x + dx, y + dy, z + dz, 0, velY, 0);    }
}
