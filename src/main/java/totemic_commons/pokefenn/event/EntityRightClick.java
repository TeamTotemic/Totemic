package totemic_commons.pokefenn.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.block.tipi.BlockTipi;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EntityRightClick
{

    @SubscribeEvent
    public void entityInteracti(PlayerInteractEvent event)
    {
        if(event != null)
        {
            if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
            {
                if(EntityUtil.raytraceFromEntity(event.world, event.entityPlayer, true, 3) != null)
                {
                    MovingObjectPosition movingObjectPosition = EntityUtil.raytraceFromEntity(event.world, event.entityPlayer, true, 3);
                    Block block = EntityUtil.getBlockFromPosition(movingObjectPosition, event.world);

                    if(block != null)
                    {
                        if(block.getMaterial() == Material.ground || (block.getUnlocalizedName().contains("dirt") || block.getUnlocalizedName().contains("grass")))
                        {
                            if(event.world.getBlock(event.x, event.y + 1, event.z) != null)
                            {
                                if(event.world.getBlock(event.x, event.y + 1, event.z) == ModBlocks.tipi)
                                {
                                    ((BlockTipi) event.world.getBlock(event.x, event.y + 1, event.z)).tipiSleep(event.world, event.x, event.y + 1, event.z, event.entityPlayer);
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}
