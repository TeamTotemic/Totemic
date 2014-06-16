package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyBeadBinding extends CeremonyBase
{
    @Override
    public void effect(TileEntity tileEntity)
    {
        TileTotemBase tileTotemBase = (TileTotemBase) tileEntity;

        int x = tileTotemBase.xCoord;
        int y = tileTotemBase.yCoord;
        int z = tileTotemBase.zCoord;

        World world = tileTotemBase.getWorldObj();

        if(tileTotemBase != null)
        {
            if(EntityUtil.getEntitiesInRange(world, x, y, z, 4, 4) != null)
            {
                for(Entity entity : EntityUtil.getEntitiesInRange(world, x, y, z, 4, 4))
                {
                    if(entity instanceof EntityItem)
                    {
                        if(((EntityItem) entity).getEntityItem().getItem() == ModItems.totemBeadSatchel)
                        {
                            //TODO Bind the totem bead satchel to the Totem Pole!
                        }
                    }
                }
            }
        }
    }
}
