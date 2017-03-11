package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.util.EntityUtil;

public class CeremonyFluteInfusion extends Ceremony
{
    public CeremonyFluteInfusion(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, int time)
    {
        if(world.isRemote)
            return;

        for(EntityItem entity : EntityUtil.getEntitiesInRange(EntityItem.class, world, pos, 5, 5))
        {
            if(entity.getEntityItem().getItem() == ModItems.flute)
            {
                EntityUtil.dropItem(world, entity.posX, entity.posY, entity.posZ, new ItemStack(ModItems.flute, 1, 1));
                entity.setDead();
            }
        }
        for(EntityPlayer player : EntityUtil.getEntitiesInRange(EntityPlayer.class, world, pos, 5, 5))
        {
            InventoryPlayer inv = player.inventory;
            for(int i = 0; i < inv.getSizeInventory(); i++)
            {
                if(inv.getStackInSlot(i).getItem() == ModItems.flute)
                    inv.setInventorySlotContents(i, new ItemStack(ModItems.flute, 1, 1));
            }
            player.inventoryContainer.detectAndSendChanges();
        }
    }
}
