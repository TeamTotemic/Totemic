package pokefenn.totemic.ceremony;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.util.EntityUtil;

public class CeremonyFluteInfusion extends Ceremony
{
    public CeremonyFluteInfusion(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, CeremonyEffectContext context)
    {
        if(world.isRemote)
            return;

        //I was gonna look at needing sugar cane nearby for this ceremony, but, no.

        for(EntityItem entity: TotemicEntityUtil.getEntitiesInRange(EntityItem.class, world, pos, 5, 5))
        {
            if(entity.getItem().getItem() == ModItems.flute)
            {
                EntityUtil.dropItem(world, entity.posX, entity.posY, entity.posZ, new ItemStack(ModItems.flute, 1, 1));
                entity.setDead();
                //cane = 0;
            }
        }
        for(EntityPlayer player: TotemicEntityUtil.getPlayersInRange(world, pos, 5, 5))
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
