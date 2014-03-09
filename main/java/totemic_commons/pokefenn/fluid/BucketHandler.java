package totemic_commons.pokefenn.fluid;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 08/03/14
 * Time: 17:42
 */
public class BucketHandler
{
    @ForgeSubscribe
    public void onBucketFill(FillBucketEvent event)
    {
        ItemStack result = fillCustomBucket(event.world, event.target);

        if (result == null)
        {
            return;
        }

        event.result = result;
        event.setResult(Event.Result.ALLOW);
    }

    public ItemStack fillCustomBucket(World world, MovingObjectPosition pos)
    {
        int blockID = world.getBlockId(pos.blockX, pos.blockY, pos.blockZ);

        if ((blockID == ModBlocks.chlorophyll.blockID) && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0)
        {
            world.setBlock(pos.blockX, pos.blockY, pos.blockZ, 0);
            return new ItemStack(ModItems.bucketChlorophyll);
        } else
        {
            return null;
        }
    }


}

