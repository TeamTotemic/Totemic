package totemic_commons.pokefenn.item.equipment;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.block.BlockCedarLog;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemBarkStripper extends ItemTotemic
{
    public int time = 0;

    public ItemBarkStripper()
    {
        super(Strings.BARK_STRIPPER_NAME);
        setMaxStackSize(1);
        setMaxDamage(126);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking())
            return false;

        Block block = world.getBlock(x, y, z);
        if(block instanceof BlockCedarLog)
        {
            if(!world.isRemote)
            {
                time++;
                if(time > 4)
                {
                    time = 0;
                    //Random random = new Random();

                    world.setBlock(x, y, z, ModBlocks.redCedarStripped);
                    //EntityItem bark = new EntityItem(world, block.blockX, block.blockY, block.blockZ, new ItemStack(ModItems.subItems, 1 + random.nextInt(3), ItemTotemicItems.cedarBark));
                    //world.spawnEntityInWorld(bark);
                    itemStack.damageItem(1, player);
                }
            }
            return true;
        }
        return false;
    }

}
