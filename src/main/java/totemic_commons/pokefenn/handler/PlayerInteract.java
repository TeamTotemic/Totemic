package totemic_commons.pokefenn.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.network.NetworkHandler;
import totemic_commons.pokefenn.network.server.PacketMouseWheel;

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
                NetworkHandler.sendToServer(new PacketMouseWheel(event.getDwheel() > 0));
                event.setCanceled(true);
            }
        }
    }
}
