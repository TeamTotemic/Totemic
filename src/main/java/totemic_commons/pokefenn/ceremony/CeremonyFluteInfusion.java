package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.ceremony.CeremonyTime;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyFluteInfusion extends Ceremony
{
    public CeremonyFluteInfusion(String modid, String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, int x, int y, int z)
    {
        if(world.isRemote)
            return;

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
