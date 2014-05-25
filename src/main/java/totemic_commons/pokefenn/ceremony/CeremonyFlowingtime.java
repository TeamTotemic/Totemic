package totemic_commons.pokefenn.ceremony;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyFlowingTime implements ICeremonyEffect
{
    @Override
    public void effect(TileEntity tileEntity)
    {
        TileTotemBase tileTotemBase = (TileTotemBase) tileEntity;

        World world = tileTotemBase.getWorldObj();

        if(tileTotemBase != null)
        {
            int radius = 12;
            int yRadius = 6;

            int x = tileTotemBase.xCoord;
            int y = tileTotemBase.yCoord;
            int z = tileTotemBase.zCoord;

            if(EntityUtil.getEntitiesInRange(world, x, y, z, 8, 8) != null)
            {
                for(Entity entity : EntityUtil.getEntitiesInRange(world, x, y, z, 8, 8))
                {
                    if(entity instanceof EntityItem)
                    {
                        if(world.getWorldTime() % 20L == 0)
                            if(((EntityItem) entity).getEntityItem().getItem() == Items.egg)
                            {
                                Random random = new Random();

                                if(random.nextInt(4) == 1)
                                {
                                    EntityChicken chicken = new EntityChicken(world);
                                    chicken.setPosition(entity.posX, entity.posY, entity.posZ);
                                    world.spawnEntityInWorld(chicken);
                                    ((EntityItem) entity).setEntityItemStack(new ItemStack(((EntityItem) entity).getEntityItem().getItem(), ((EntityItem) entity).getEntityItem().stackSize - 1, 0));
                                }
                            }
                    }
                }
            }

            for(int i = -radius; i <= radius; i++)
                for(int j = -yRadius; j <= yRadius; j++)
                    for(int k = -radius; k <= radius; k++)
                    {
                        if(world.getBlock(x + i, y + j, z + k) != null)
                        {
                            Block block = world.getBlock(x + i, y + j, z + k);

                            if(block instanceof IPlantable)
                            {
                                Random rand = new Random();
                                if(rand.nextInt(30) == 1)
                                    block.updateTick(world, x + i, y + j, z + k, rand);
                            }
                        }
                    }


        }
    }
}
