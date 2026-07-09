package totemic_commons.pokefenn.ceremony;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
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

        AxisAlignedBB aabb = EntityUtil.getAABBAround(x, y, z, 6, 6);

        for(EntityItem entity : world.selectEntitiesWithinAABB(EntityItem.class, aabb, entity -> isRegularFlute(((EntityItem) entity).getEntityItem())))
        {
            EntityUtil.dropItem(world, entity.posX, entity.posY, entity.posZ, new ItemStack(ModItems.flute, 1, 1));
            entity.setDead();
        }

        for(EntityPlayer player : world.selectEntitiesWithinAABB(EntityPlayer.class, aabb, IEntitySelector.selectAnything))
        {
            InventoryPlayer inv = player.inventory;
            for(int i = 0; i < inv.getSizeInventory(); i++)
            {
                if(isRegularFlute(inv.getStackInSlot(i)))
                {
                    inv.setInventorySlotContents(i, new ItemStack(ModItems.flute, 1, 1));
                }
            }
        }
    }

    private static boolean isRegularFlute(ItemStack stack)
    {
        return stack != null && stack.getItem() == ModItems.flute && stack.getItemDamage() != 1;
    }
}
