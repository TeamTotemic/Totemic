package totemic_commons.pokefenn.ceremony;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.ceremony.CeremonyTime;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyZaphkielWaltz extends Ceremony
{

    public CeremonyZaphkielWaltz(String modid, String name, int musicNeeded, CeremonyTime maxStartupTime, CeremonyTime effectTime,
            int musicPer5, MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, effectTime, musicPer5, instruments);
    }

    @Override
    public void effect(World world, int x, int y, int z)
    {
        int radius = 6;

        if(!world.isRemote && world.getWorldTime() % 20L == 0)
        {
            for(Entity entity : EntityUtil.getEntitiesInRange(world, x, y, z, radius, radius))
            {
                if(entity instanceof EntityItem)
                {
                    EntityItem item = (EntityItem)entity;
                    if(item.getEntityItem().getItem() == Items.egg)
                    {
                        if(world.rand.nextInt(4) == 0)
                        {
                            EntityChicken chicken = new EntityChicken(world);
                            chicken.setPosition(entity.posX, entity.posY, entity.posZ);
                            world.spawnEntityInWorld(chicken);
                            if(item.getEntityItem().stackSize == 1)
                                item.setDead();
                            else
                            {
                                ItemStack stack = item.getEntityItem().copy();
                                stack.stackSize--;
                                item.setEntityItemStack(stack);
                            }
                        }
                    }
                }
            }
        }

        if(world.getWorldTime() % 5L == 0)
        {
            for(int i = -radius; i <= radius; i++)
                for(int j = -radius; j <= radius; j++)
                    for(int k = -radius; k <= radius; k++)
                    {
                        Block block = world.getBlock(x + i, y + j, z + k);
                        if(block == Blocks.sapling)
                        {
                            world.setBlock(x + i, y + j, z + k, ModBlocks.totemSapling, 0, 3);
                            spawnParticles(world, x + i + 0.5, y + j + 0.5, z + k + 0.5);
                        }
                        else if(block instanceof IGrowable && block.getTickRandomly())
                        {
                            if(world.rand.nextInt(4) == 0)
                            {
                                if(!world.isRemote)
                                    block.updateTick(world, x + i, y + j, z + k, world.rand);
                                spawnParticles(world, x + i + 0.5, y + j + 0.6, z + k + 0.5);
                            }
                        }
                    }
        }
    }

    private void spawnParticles(World world, double x, double y, double z)
    {
        if(world.isRemote)
        {
            double dx = world.rand.nextGaussian();
            double dy = world.rand.nextGaussian() * 0.5;
            double dz = world.rand.nextGaussian();
            double velY = world.rand.nextGaussian();
            world.spawnParticle("happyVillager", x + dx, y + dy, z + dz, 0, velY, 0);
        }
    }
}
