package totemic_commons.pokefenn.ceremony;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyPotion extends CeremonyBase
{
    @Override
    public void effect(TileEntity tileEntity)
    {
        TileTotemBase tiletotemBase = (TileTotemBase) tileEntity;

        int xCoord = tileEntity.xCoord;
        int yCoord = tileEntity.yCoord;
        int zCoord = tileEntity.zCoord;
        World worldObj = tiletotemBase.getWorldObj();

        /*if(tiletotemBase != null)
        {
            if(EntityUtil.getEntitiesInRange(worldObj, xCoord, yCoord, zCoord, 6, 6) != null)
            {
                for(Entity entity : EntityUtil.getEntitiesInRange(worldObj, xCoord, yCoord, zCoord, 6, 6))
                {
                    if(entity instanceof EntityItem)
                    {
                        for(CeremonyPotionRegistry ceremonyPotionRegistry : CeremonyPotionRegistry.ceremonyRegistry)
                        {
                            if(((EntityItem) entity).getEntityItem().getItem() == ceremonyPotionRegistry.getItem().getItem() && ((EntityItem) entity).getEntityItem().getItemDamage() == ceremonyPotionRegistry.getItem().getItemDamage() && ((EntityItem) entity).getEntityItem().stackSize >= ceremonyPotionRegistry.getItem().stackSize)
                            {
                                ((EntityItem) entity).setEntityItemStack(new ItemStack(((EntityItem) entity).getEntityItem().getItem(), ((EntityItem) entity).getEntityItem().stackSize - ceremonyPotionRegistry.getItem().stackSize, ((EntityItem) entity).getEntityItem().getItemDamage()));

                                if(worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 8) != null)
                                {
                                    EntityPlayer player = worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 8);

                                    TotemUtil.addPotionEffects(player, ceremonyPotionRegistry.getLength(), 10, ceremonyPotionRegistry.getPotion(), ceremonyPotionRegistry.getStrength(), true);
                                }
                            }
                        }
                    }
                }
            }
        }
        */
    }
}
