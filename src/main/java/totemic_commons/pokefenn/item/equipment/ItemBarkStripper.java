package totemic_commons.pokefenn.item.equipment;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.block.BlockCedarLog;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.item.ItemTotemicItems;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemBarkStripper extends ItemTotemic
{
    public int time = 0;

    public ItemBarkStripper()
    {
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.BARK_STRIPPER_NAME);
        setMaxStackSize(1);
        setMaxDamage(126);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if(!world.isRemote)
        {
            MovingObjectPosition block = EntityUtil.raytraceFromEntity(world, player, true, 5);

            if(block != null && !player.isSneaking())
            {
                Block blockQuery = EntityUtil.getBlockFromPosition(block, player.worldObj);

                if(blockQuery instanceof BlockCedarLog)
                {
                    time++;
                    if(time > 4)
                    {
                        time = 0;
                        Random random = new Random();

                        world.setBlock(block.blockX, block.blockY, block.blockZ, ModBlocks.redCedarStripped);
                        EntityItem bark = new EntityItem(world, block.blockX, block.blockY, block.blockZ, new ItemStack(ModItems.subItems, 1 + random.nextInt(3), ItemTotemicItems.cedarBark));
                        world.spawnEntityInWorld(bark);
                        itemStack.damageItem(1, player);
                    }
                }

            }
        }
        return true;
    }

}
