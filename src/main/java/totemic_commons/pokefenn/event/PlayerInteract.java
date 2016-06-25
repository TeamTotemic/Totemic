package totemic_commons.pokefenn.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.server.PacketMouseWheel;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PlayerInteract
{
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onMouse(MouseEvent event)
    {
        if(event.isCanceled())
            return;

        if(event.getDwheel() != 0)
        {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if(player.isSneaking() && player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == ModItems.totemWhittlingKnife)
            {
                PacketHandler.sendToServer(new PacketMouseWheel(event.getDwheel() > 0));
                event.setCanceled(true);
            }
        }
    }
}
