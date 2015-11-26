package totemic_commons.pokefenn.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.block.tipi.BlockTipi;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.server.PacketMouseWheel;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class PlayerInteract
{

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event)
    {
        if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
        {
            if(event.world.getBlock(event.x, event.y + 1, event.z) == ModBlocks.tipi)
            {
                Block block = event.world.getBlock(event.x, event.y, event.z);
                if(block != null)
                {
                    if(block.getMaterial() == Material.ground || block.getUnlocalizedName().contains("dirt") || block.getUnlocalizedName().contains("grass"))
                    {
                        ((BlockTipi)ModBlocks.tipi).tipiSleep(event.world, event.x, event.y + 1, event.z, event.entityPlayer);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onMouse(MouseEvent event)
    {
        if(event.isCanceled())
            return;

        if(event.dwheel != 0)
        {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if(player.isSneaking() && player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.totemWhittlingKnife)
            {
                PacketHandler.sendToServer(new PacketMouseWheel(event.dwheel > 0));
                event.setCanceled(true);
            }
        }
    }
}
