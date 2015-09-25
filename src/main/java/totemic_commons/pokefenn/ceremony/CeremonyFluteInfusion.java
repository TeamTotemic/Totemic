package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyFluteInfusion extends CeremonyBase
{
    @Override
    public void effect(TileEntity tileEntity)
    {
        if(tileEntity instanceof TileTotemBase)
        {
            TileTotemBase tileTotemBase = (TileTotemBase) tileEntity;

            int x = tileTotemBase.xCoord;
            int y = tileTotemBase.yCoord;
            int z = tileTotemBase.zCoord;

            World world = tileTotemBase.getWorldObj();
            for(Entity entity : EntityUtil.getEntitiesInRange(world, x, y, z, 5, 5))
            {
                if(entity instanceof EntityItem)
                {
                    if(((EntityItem) entity).getEntityItem().getItem() == ModItems.flute)
                    {
                        EntityUtil.dropItem(world, entity.posX, entity.posY, entity.posZ, new ItemStack(ModItems.flute, 1, 1));
                        entity.setDead();
                    }
                }
            }
        }
    }
}
