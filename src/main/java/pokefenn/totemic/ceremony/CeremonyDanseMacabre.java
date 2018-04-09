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

        if(!world.isRemote && context.getTime() % 20 == 0)
        {
            for(EntityItem entity: EntityUtil.getEntitiesInRange(EntityItem.class, world, pos, radius, radius))
            {
                if(entity.getItem().getItem() == Items.ROTTEN_FLESH)
                {
                    if(world.rand.nextInt(4) == 0)
                    {
                        if(entity.dimension == -1)
                        {
                            EntityPigZombie pigZombie = new EntityPigZombie(world);
                            summon(world, pigZombie, entity);
                        }
                        else if(world.rand.nextInt(10) == 0)
                        {
                            EntityZombieVillager zombieVillager = new EntityZombieVillager(world);
                            summon(world, zombieVillager, entity);
                        }
                        else
                        {
                            EntityZombie zombie = new EntityZombie(world);
                            summon(world, zombie, entity);
                        }
                    }
                }
            }
        }

    }

    private void summon(World world, EntityZombie zombie, EntityItem item)
    {
        BlockPos spawnPos = item.getPosition();
        double x = item.posX;
        double y = item.posY;
        double z = item.posZ;

        if(world.isAirBlock(spawnPos) && world.isAirBlock(spawnPos.up()))
        {
            zombie.setPosition(x, y, z);
            zombie.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20 * 30, 1));
            zombie.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20 * 30, 1));
            world.spawnEntity(zombie);
            if(item.getItem().getCount() == 1)
                item.setDead();
            else
            {
                ItemStack stack = item.getItem().copy();
                stack.shrink(1);
                item.setItem(stack);
            }
            ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z, 24, 0.6D, 0.5D, 0.6D, 1.0D);
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
}
