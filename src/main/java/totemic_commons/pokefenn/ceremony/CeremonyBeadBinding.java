package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.tileentity.totem.TileCeremonyIntelligence;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyBeadBinding implements ICeremonyEffect
{
    @Override
    public void effect(TileEntity tileEntity)
    {
        TileCeremonyIntelligence tileCeremonyIntelligence = (TileCeremonyIntelligence) tileEntity;

        int x = tileCeremonyIntelligence.xCoord;
        int y = tileCeremonyIntelligence.yCoord;
        int z = tileCeremonyIntelligence.zCoord;

        World world = tileCeremonyIntelligence.getWorldObj();

        if(tileCeremonyIntelligence != null)
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
