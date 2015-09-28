package totemic_commons.pokefenn.ceremony;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyZaphkielWaltz extends CeremonyBase
{

    @Override
    public void effect(TileEntity tileEntity)
    {
        TileTotemBase tileTotemBase = (TileTotemBase) tileEntity;
        World world = tileTotemBase.getWorldObj();

        if(tileTotemBase != null)
        {
            int radius = 6;
            int x = tileTotemBase.xCoord;
            int y = tileTotemBase.yCoord;
            int z = tileTotemBase.zCoord;

            if(world.getWorldTime() % 20L == 0)
            {
                for(Entity entity : EntityUtil.getEntitiesInRange(world, x, y, z, 8, 8))
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
                            if(block instanceof IGrowable && block.getTickRandomly())
                            {
                                if(world.rand.nextInt(4) == 0)
                                {
                                    block.updateTick(world, x + i, y + j, z + k, world.rand);
                                }
                            }
                        }
            }
        }
    }
}
