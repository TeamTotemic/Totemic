package pokefenn.totemic.ceremony;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.util.EntityUtil;

public class CeremonyCleansing extends Ceremony
{

    public CeremonyCleansing(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, CeremonyEffectContext context)
    {
        int radius = 6;

        if (!world.isRemote && context.getTime() % 65 * 10 == 0)
        {
            for (EntityLiving entity : EntityUtil.getEntitiesInRange(EntityLiving.class, world, pos, radius, radius))
            {
                if(entity.isPotionActive(MobEffects.WEAKNESS))
                {
                    double x = entity.posX;
                    double y = entity.posY;
                    double z = entity.posZ;

                    if (entity instanceof EntityZombieVillager)
                    {
                        entity.setDead();
                        spawnParticles(world, x, y, z);
                        EntityVillager villager = new EntityVillager(world);
                        villager.setPosition(x, y, z);
                        world.spawnEntity(villager);
                        return;
                    } else if (entity instanceof EntityPigZombie)
                    {
                        entity.setDead();
                        spawnParticles(world, x, y, z);
                        EntityPig pig = new EntityPig(world);
                        pig.setPosition(x, y, z);
                        world.spawnEntity(pig);
                        return;
                    }
                }
            }

        }


    }

    @Override
    public int getEffectTime()
    {
        return 10 * 20;
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
            ((WorldServer) world).spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, x + dx, y + dy, z + dz, 0, velY, 0);
    }
}

