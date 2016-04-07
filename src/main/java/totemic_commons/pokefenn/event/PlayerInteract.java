package totemic_commons.pokefenn.event;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.ModBlocks;
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
    public void onRightClickBlock(RightClickBlock event)
    {
        World world = event.getEntityPlayer().getEntityWorld();
        if(world.getBlockState(event.getPos().up()).getBlock() == ModBlocks.tipi)
        {
            IBlockState state = world.getBlockState(event.getPos());
            Block block = state.getBlock();
            if(block != null)
            {
                if(block.getMaterial(state) == Material.ground || block.getUnlocalizedName().contains("dirt") || block.getUnlocalizedName().contains("grass"))
                {
                    ModBlocks.tipi.tipiSleep(world, event.getPos(), event.getEntityPlayer());
                }
            }
        }
    }

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
